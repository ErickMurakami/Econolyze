# AI service

Provides financial chat, retrieval of embedded financial context, and tools for querying the financial service and performing calculations. It runs on port **8083**, using Java 21 and Quarkus 3.34.6 with LangChain4j.

Chat uses Groq's OpenAI-compatible API. Embeddings use Gemini with 768 dimensions, stored in PostgreSQL through pgvector. `ChatService` coordinates the assistant; REST resources, financial clients, tools, conversation memory, and embedding jobs retain their existing packages.

## Run locally

From this directory:

```bash
mvn quarkus:dev
```

Configure `GROQ_API_KEY`, `GEMINI_API_KEY`, `DB_USERNAME`, `DB_PASSWORD`, `DB_JDBC_URL`, `DB_URL`, `DB_HOST`, `DB_PORT`, `DB_NAME`, and `INTERNAL_SECRET`. Set `MP_JWT_VERIFY_PUBLICKEY_LOCATION` to the authentication service's public key. For local processes, set `QUARKUS_REST_CLIENT_FINANCIAL_SERVICE_URL=http://localhost:8082` instead of the default container hostname.

The shared internal secret must match the financial service. It signs the service token used in `X-Internal-Token`. Never put API keys or signing secrets in tracked files. Model request/response logging is disabled to avoid recording financial prompts and responses. All three configured timeouts use ISO-8601 `PT60S` (60 seconds), accepted by Java Duration parsing and Quarkus configuration tooling.

JDBC requires TLS by default. For a local database without TLS, override `QUARKUS_DATASOURCE_JDBC_ADDITIONAL_JDBC_PROPERTIES_SSLMODE=disable` locally.

Flyway applies `V1.2.0__Init_AI_Schema.sql`, including the vector extension and the `ai` schema. The database must support pgvector and allow the migration's extension creation. Automatic Flyway repair is currently enabled and needs review before production use.

The chat endpoint is `/ai/chat`; the gateway exposes it as `/api/ai/chat`. The existing `/dev/rag` resources need access restrictions before production exposure. Retrieval ownership is also a review item; see the [code review](../../docs/backend-review.md).

## Build and test

```bash
mvn clean verify
java -jar target/quarkus-app/quarkus-run.jar
```

Current tests exercise the local financial calculation tools, including zero months and deficit precision. They do not start the AI runtime or call external model providers. JaCoCo reports are at `target/site/jacoco/index.html`. A packaged build does not verify provider credentials, pgvector retrieval, or startup migrations.

See the [project README](../../README.md) for full environment configuration and the other services.
