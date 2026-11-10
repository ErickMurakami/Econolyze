package dev.econolyze.integration;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.Map;
import java.util.UUID;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class PostgresTestResource implements QuarkusTestResourceLifecycleManager {
    private PostgreSQLContainer database;

    @Override
    public Map<String, String> start() {
        database = new PostgreSQLContainer("postgres:17-alpine")
                .withDatabaseName("econolyze_test")
                .withUsername("econolyze_test")
                .withPassword(UUID.randomUUID().toString());
        database.start();
        String publicKey;
        try {
            var generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            publicKey = "-----BEGIN PUBLIC KEY-----\n"
                    + Base64.getMimeEncoder(64, new byte[]{'\n'})
                    .encodeToString(generator.generateKeyPair().getPublic().getEncoded())
                    + "\n-----END PUBLIC KEY-----";
        } catch (Exception e) {
            database.stop();
            throw new IllegalStateException(
                    "Cannot generate test verification key",
                    e
            );
        }
        return Map.of(
                "mp.jwt.verify.publickey", publicKey,
                "quarkus.datasource.username", database.getUsername(),
                "quarkus.datasource.password", database.getPassword(),
                "quarkus.datasource.jdbc.url", database.getJdbcUrl(),
                "quarkus.datasource.reactive.url", database.getJdbcUrl().replace("jdbc:", ""),
                "quarkus.datasource.jdbc.additional-jdbc-properties.sslmode", "disable",
                "quarkus.scheduler.enabled", "false",
                "banco-central-api/mp-rest/url", "http://localhost:1",
                "internal.secret", UUID.randomUUID().toString());
    }

    @Override
    public void stop() {
        if (database != null) database.stop();
    }
}
