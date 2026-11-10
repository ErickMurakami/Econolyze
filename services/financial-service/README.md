# Financial service

Owns accounts, consolidated balances, transactions, payments, recurring templates, goals, reports, and CDI projections. It runs on port **8082**, using Java 21, Quarkus 3.32.2, Hibernate Reactive/Panache, and PostgreSQL.

The existing package structure separates application services and DTOs, domain entities, repositories and external clients, and REST resources. MapStruct handles DTO/entity mappings. Reactive reads use `@WithSession`; writes use `@WithTransaction`. CDI rates are refreshed by the existing scheduled jobs and cached with a fallback value.

Entities use explicit constructors and accessors. Payment and balance constructors cover their usual creation paths, and the recurring-transaction constructor copies a template's values. Immutable DTOs are records; DTOs populated in stages remain mutable classes. There are no DTO or entity builders.

## Run locally

From this directory:

```bash
mvn quarkus:dev
```

Use Java 21 and configure `DB_USERNAME`, `DB_PASSWORD`, `DB_JDBC_URL`, `DB_URL`, `BANCO_CENTRAL_API_URL`, and `INTERNAL_SECRET`. Set `MP_JWT_VERIFY_PUBLICKEY_LOCATION` to the public key matching the authentication service. JDBC and reactive URLs must point to the same PostgreSQL database. See the [environment table](../../README.md#requirements-and-configuration) for formats.

JDBC requires TLS by default. Override `QUARKUS_DATASOURCE_JDBC_ADDITIONAL_JDBC_PROPERTIES_SSLMODE=disable` only for a local database without TLS.

Flyway applies `V1.1.0__Init_Finance_Schema.sql` to the `finance` schema at startup. Monetary columns use `NUMERIC(19,4)`. This refactoring does not change migrations or rounding policies.

REST resources include `/api/accounts`, `/api/balance`, `/api/transaction`, `/api/payment`, `/api/recurring-template`, `/api/goal`, `/api/dashboard`, `/api/category`, and `/api/cdi`. Internal routes additionally require `X-Internal-Token`. Use the gateway for the frontend's normal API entry point.

## Build and test

```bash
mvn clean verify
java -jar target/quarkus-app/quarkus-run.jar
```

Unit tests cover projections, goal progress, CDI fallback, rejected payments, transaction states, entity construction, and DTO JSON. Coverage is reported at `target/site/jacoco/index.html`.

Run PostgreSQL integration tests with Docker available:

```bash
mvn -Pintegration clean verify
```

The profile starts PostgreSQL 17 through Testcontainers and applies the actual migration. It checks category HTTP output, internal-token rejection, monetary precision, and JDBC rollback. The three integration scenarios passed locally using Podman’s Docker-compatible API. They do not establish coverage of Hibernate Reactive write transactions or concurrent payments.

`ReportService` and `BudgetService` are placeholders; `/api/report` currently returns the literal string `report`. Reporting and budgeting should not be treated as completed features.

Ownership checks, payment-status aggregation, cached paid totals, and concurrent balance changes remain the most important follow-up work. See the [code review](../../docs/backend-review.md).

## Mapper generation in the IDE

MapStruct 1.6.3 is configured as a Maven annotation processor. The POM enables JDT/m2e annotation processing and all mapper targets must be mapped or explicitly ignored. Generated implementations belong under `target/generated-sources/annotations`; do not edit them.

To rebuild the implementations with diagnostic output:

```bash
mvn clean compile -Dmapstruct.verbose=true
```

If the IDE reports “No implementation was created … erroneous element null” while this command succeeds, refresh the Maven project and clean the Java language-server workspace. The IDE may retain processor or type information from before the model changes. In VS Code, use **Java: Clean Java Language Server Workspace** and reload the project. This configuration follows [MapStruct’s IDE guidance](https://mapstruct.org/documentation/ide-support/).
