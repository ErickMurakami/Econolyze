# Authentication service

Handles registration, password verification, access and refresh token issuance, refresh rotation, and logout. It runs on port **8081** and uses Java 21 with Quarkus 3.32.2.

`AuthResource` exposes `/auth/register`, `/auth/login`, `/auth/refresh`, `/auth/logout`, and `/auth/ping`. `UserService` owns the application flow. Users and revoked refresh tokens are stored through blocking Hibernate ORM/Panache; writes use Jakarta transactions. Passwords are hashed with BCrypt, and tokens are signed with RSA.

## Run locally

From this directory, with Java 21 and Maven installed:

```bash
mvn quarkus:dev
```

Configure `DB_USERNAME`, `DB_PASSWORD`, and `DB_JDBC_URL`. Supply a private signing key through `SMALLRYE_JWT_SIGN_KEY_LOCATION` and its matching public key through `MP_JWT_VERIFY_PUBLICKEY_LOCATION`. The configured issuer is `econolyze`. Keys and credentials are not included in the repository.

JDBC requires TLS by default. For a local database without TLS, override `QUARKUS_DATASOURCE_JDBC_ADDITIONAL_JDBC_PROPERTIES_SSLMODE=disable` in the local environment.

Flyway applies `src/main/resources/db/migration/V1.0.0__Init_Auth_Schema.sql` at startup. It creates the `authentication` schema, users, and revoked-token storage. Do not edit an already applied migration.

## Build and test

```bash
mvn clean verify
java -jar target/quarkus-app/quarkus-run.jar
```

Current tests check login DTO JSON, Bean Validation, canonical construction, and credential-safe diagnostic output. They do not cover database-backed registration or refresh-token concurrency. JaCoCo reports are written to `target/site/jacoco/index.html` after verification.

The admin endpoint uses a lowercase role while stored role values are uppercase; that inconsistency and refresh-token races remain review items. See the [backend review](../../docs/backend-review.md) and [project setup](../../README.md).
