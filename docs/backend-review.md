# Backend code review

Reviewed on 10 November 2026.

The existing service boundaries are reasonable for this project. Authentication owns users and tokens; the financial service owns accounts and financial operations; the gateway forwards and aggregates requests; the AI service handles chat and retrieval. I would keep that structure. The areas that need more attention are ownership checks, payment consistency, and the tests around persistence.

All services target Java 21. Auth, financial, and gateway remain on Quarkus 3.32.2; AI remains on 3.34.6. The financial service uses Hibernate Reactive, while authentication uses blocking Hibernate ORM. Both approaches fit their current implementations. Existing dependency versions and migration history were left in place.

## Changes made

Lombok has been removed from source code, dependencies, and annotation processing. MapStruct remains configured. The clean verbose build generated all eight mapper implementations. JDT/m2e annotation processing is enabled as recommended in [MapStruct’s IDE documentation](https://mapstruct.org/documentation/ide-support/). Unmapped targets now fail compilation; intentionally absent ownership, relationship, identity, or calculated fields are explicitly ignored, retaining their existing values. Mapper tests cover account creation/update and payment DTO round-trips. IDE diagnostics may still require a Maven refresh and Java language-server workspace cleanup. Entities keep their JPA constructors and identity equality. Immutable DTOs are records with component accessors; TransactionDTO, InvestmentProjectionDTO, and GoalProgressDTO remain classes because the services still populate them in stages.

DTO and entity builders added little value at the existing call sites. They have been removed, qualified type names replaced with imports, and gaps left by removed annotations cleaned up. The remaining DTO classes retain their existing accessors and equality behavior.

Entity construction now has a few focused entry points:

- `Payment(transaction, accountId, amount, method, paidAt)` creates the completed payment used by transaction creation.
- `Balance(userId, date)` initializes the existing zero totals.
- The recurring-template constructor sets the first occurrence, active flag, and processed count.
- `Transaction(template)` copies the values previously assigned by the recurring-service builder.

These constructors keep creation details close to the entity. Transaction boundaries and recurrence scheduling remain in the services. I would avoid moving more behavior until payment and recurrence rules have better tests. In particular, the recurring transaction still starts with null status and payments, matching its previous construction; the existing lifecycle callback supplies PENDING. Changing those defaults should be considered separately.

Login DTO diagnostic output redacts credentials. AI prompt/response logging is disabled, and the gateway no longer logs chat responses. AI timeout values use ISO-8601 PT60S; both Java Duration and Quarkus duration parsing were checked against the previous 60-second values. [Quarkus supports both duration formats](https://quarkus.io/guides/http-reference/#duration-note-anchor); the ISO form also avoids the IDE parser error. Existing recurrence logs and exception messages still need attention.

The gateway YAML used scalar logging categories where Quarkus expects a nested `level`, and singular TLS certificate properties that were ignored. Those entries are corrected. TLS files now take effect, using the existing deployment paths or `TLS_CERTIFICATE_FILES` and `TLS_KEY_FILES`. Local HTTP runs and tests must explicitly clear the certificate properties when files are absent. The ineffective JSON-console setting was removed because the JSON logging extension is not installed. These changes follow the [HTTP configuration reference](https://quarkus.io/guides/http-reference/) and [logging reference](https://quarkus.io/guides/logging/).

Each service now has a README covering its actual responsibilities, configuration, migration, commands, and testing gaps. The main README is in English, with a Portuguese version in README_pt.md.

## Findings to address next

**Critical — restrict the AI development resources.** `RagDevResource` exposes indexing and search without an explicit access restriction, and search accepts a caller-supplied userId. Restrict these routes to development or an agreed administrative role, and derive ordinary-user ownership from authentication. This changes permissions and needs review before implementation.

**High — scope ID lookups to the owner.** Goal/payment lookups and payment account selection do not consistently include the authenticated user. Add owner predicates and two-user tests. Decide whether inaccessible records return 403 or 404 before changing the API.

**High — review cookie authentication and CSRF together.** `SecurityFilter` injects cookie credentials into an Authorization header before the bearer-header shortcut. That can bypass the intended CSRF branch. Local-environment detection also trusts the Host header. Track the original credential source and remove request-header-based trust; this changes browser authentication behavior and needs approval.

**High — make payment totals consistent.** `Transaction.getPaidAmount()` counts COMPLETED payments, while `recalculateStatus()` sums every payment. The cached paid total is also never invalidated after mutations. Agree on how pending, failed, and refunded payments affect totals, status, and account balances, then use that rule consistently. The current refactoring does not choose a new financial rule.

**High — protect concurrent balance changes.** Payment validation and account updates use read/modify/write without a shared transaction-row lock or account version. Simultaneous payments can both pass validation. Balance has a version and pessimistic lookup, but its first insert can still race. Add PostgreSQL concurrency tests before choosing locking, atomic updates, or a version-column migration.

**Medium — cover calculation and error boundaries.** Percentage-of-CDI projection divides by zero for a zero principal; goal progress also divides by the goal amount. Request validation and exception mapping vary across services, with several resources converting failures to generic responses. Define these contracts and add tests before changing them. The stored ADMIN role and lowercase admin endpoint restriction also need a consistent spelling.

**Medium — verify persistence behavior on PostgreSQL.** Collection fetch joins with pagination, per-goal queries, and concurrent Uni combinations over a reactive session deserve runtime checks. Application startup accepted the existing RecurringTemplate mapping on PostgreSQL; relationship round-trips still need coverage. Existing indexes cover user/date transaction queries, payment transaction IDs, and account owners; there is no measured workload supporting additional indexes yet. Monetary schema columns remain NUMERIC(19,4), and existing rounding policies remain unchanged.

**Medium — finish configuration and operations checks.** AI still enables Flyway repair at startup. Financial's scheduler timezone property and auth's reactive cache property are ignored by their installed extensions. Correcting the actual scheduler timezone can change execution times, so that needs a deliberate decision. Dependency vulnerability scanning, refresh-token races, and retrieval ownership also remain open.

Reporting and budgeting are also unfinished: ReportService and BudgetService are placeholders, and the report endpoint returns a fixed string. The READMEs now call that out explicitly.

## Test results

The original financial build succeeded with no tests executed because both starter files were commented out. Gateway's starter test targeted a nonexistent `/hello` route; AI's starter test required unavailable runtime infrastructure. Those tests have been replaced or supplemented with checks against existing behavior.

Current standard verification uses `mvn clean verify` in each service:

| Service | Tests | Result |
| --- | ---: | --- |
| Auth | 4 | Passed |
| Financial | 24 | Passed |
| Gateway | 2 | Passed |
| AI | 3 | Passed |

Coverage includes projections, invalid inputs, goal progress, CDI fallback, payment rejection, transaction states, entity construction, DTO JSON/validation, gateway ping and anonymous access, and AI calculation tools. The tests do not establish complete financial or security coverage.

The PostgreSQL integration profile passed with all three scenarios, using a temporary Podman API socket and PostgreSQL 17. Flyway applied the actual migration, and Quarkus started successfully. The tests checked category output, missing internal-token rejection, NUMERIC(19,4) precision, and JDBC rollback isolation. An earlier attempt without a container socket failed at startup; the successful run resolves that environmental limitation. Hibernate Reactive writes, successful payment persistence, authenticated ownership, and concurrency still need tests. Test containers were stopped after execution.

Backend validation now runs automatically on pushes and pull requests, including PostgreSQL integration on the hosted Docker runtime. Deployment runs only by workflow dispatch and depends on validation. Both workflow files passed actionlint and checks of their triggers, matrix, permissions, and job dependencies. Publishing and deployment were not executed. No history was rewritten; the modernization is split into service, CI, configuration, and documentation commits using the existing author identity and actual commit times.

Measured JaCoCo results are listed below. They include explicit boilerplate and unused classes; they should be read alongside the missing scenarios, rather than as a quality score. Standard-agent coverage of the Quarkus test runtime may also be incomplete.

| Service | Lines | Branches |
| --- | ---: | ---: |
| Auth | 4/195 (2.05%) | 0/50 (0.00%) |
| Financial | 341/1928 (17.69%) | 35/564 (6.21%) |
| Gateway | 21/387 (5.43%) | 6/182 (3.30%) |
| AI | 4/290 (1.38%) | 0/86 (0.00%) |

Reports are generated under each service's `target/site/jacoco/`. Important gaps remain in refresh rotation, successful payments and account updates, refunds, repeated payments after cached reads, reactive rollback, recurrence scheduling, reports, and external AI calls.

Source/build searches and filtered dependency trees found no remaining Lombok usage. Compilation and Quarkus augmentation are part of the clean builds. No Checkstyle, SpotBugs, or PMD configuration is present. Deployment startup, native builds, frontend checks, and vulnerability scanning were not executed.
