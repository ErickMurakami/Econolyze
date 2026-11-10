# API gateway

The frontend's API entry point. It forwards requests to authentication, financial, and AI services and aggregates financial responses. The gateway uses Java 21 and Quarkus 3.32.2, listening on HTTP **8080** and HTTPS **8443** when certificates are supplied.

REST resources, clients, aggregators, filters, and exception mappers remain in their existing packages. JWT verification uses the authentication service's public key. The cookie-to-header and CSRF flow is implemented in `SecurityFilter`; its current security limitations are described in the [code review](../../docs/backend-review.md).

## Run locally

From this directory, with Java 21 and Maven:

```bash
export AUTH_SERVICE_URL=http://localhost:8081
export FINANCIAL_SERVICE_URL=http://localhost:8082
export AI_SERVICE_URL=http://localhost:8083
mvn quarkus:dev
```

Set `MP_JWT_VERIFY_PUBLICKEY_LOCATION` to the matching public verification key. The gateway has no database.

`src/main/resources/application.yml` configures the service URLs, CORS, TLS, logging, and Swagger. TLS paths default to `/work/ssl/cert.pem` and `/work/ssl/key.pem`; override them with `TLS_CERTIFICATE_FILES` and `TLS_KEY_FILES`. These use Quarkus's supported plural certificate properties and are now effective. Provide valid files when using HTTPS.

For an HTTP-only local run without those mounted certificates:

```bash
mvn quarkus:dev -Dquarkus.http.ssl.certificate.files= -Dquarkus.http.ssl.certificate.key-files=
```

The production profile requires all three downstream URL variables. Logging uses the standard console format; JSON logging is not enabled because the project does not include the JSON logging extension.

`/api/ping` returns `pong`. Swagger UI is at `/swagger-ui`, and OpenAPI is at `/q/openapi`. Chat is exposed at `/api/ai/chat`; other route groups are listed in the [main README](../../README.md#features-and-api).

## Build and test

```bash
mvn clean verify
java -jar target/quarkus-app/quarkus-run.jar
```

Quarkus/REST Assured tests verify ping and anonymous account-access rejection. Tests generate a temporary verification key and override downstream URLs, so production keys and running downstream services are not required. They do not yet cover authenticated forwarding, aggregation failures, or CSRF behavior. Coverage is reported at `target/site/jacoco/index.html`.
