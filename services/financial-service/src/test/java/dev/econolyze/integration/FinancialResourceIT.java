package dev.econolyze.integration;

import io.agroal.api.AgroalDataSource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(PostgresTestResource.class)
class FinancialResourceIT {
    @Inject
    AgroalDataSource dataSource;

    @Test
    void categoryEndpointReturnsExistingDomainCategories() {
        given().when().get("/api/category")
                .then().statusCode(200).body("", hasItems("FOOD", "TRANSPORT", "OTHER"));
    }

    @Test
    void internalEndpointRejectsMissingServiceToken() {
        given().when().get("/api/transaction/internal/rag")
                .then().statusCode(401);
    }

    @Test
    void migrationStoresMonetaryPrecisionAndRollbackIsolatesAccountData() throws Exception {
        try (var connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                long accountId;
                try (var insert = connection.prepareStatement(
                        "INSERT INTO finance.accounts(user_id, name, actual_balance) VALUES (?, ?, ?) RETURNING id")) {
                    insert.setLong(1, 9001L);
                    insert.setString(2, "Integration account");
                    insert.setBigDecimal(3, new BigDecimal("123.4567"));
                    try (var result = insert.executeQuery()) {
                        assertTrue(result.next());
                        accountId = result.getLong(1);
                    }
                }
                try (var query = connection.prepareStatement("SELECT actual_balance FROM finance.accounts WHERE id = ?")) {
                    query.setLong(1, accountId);
                    try (var result = query.executeQuery()) {
                        assertTrue(result.next());
                        assertEquals(new BigDecimal("123.4567"), result.getBigDecimal(1));
                    }
                }
                connection.rollback();
                try (var query = connection.prepareStatement("SELECT count(*) FROM finance.accounts WHERE id = ?")) {
                    query.setLong(1, accountId);
                    try (var result = query.executeQuery()) {
                        assertTrue(result.next());
                        assertEquals(0, result.getInt(1));
                    }
                }
            } finally {
                connection.rollback();
            }
        }
    }
}
