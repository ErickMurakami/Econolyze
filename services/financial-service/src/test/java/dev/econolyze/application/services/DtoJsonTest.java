package dev.econolyze.application.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.econolyze.application.dto.*;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.SerializationFeature;

import static org.junit.jupiter.api.Assertions.*;

class DtoJsonTest {
    ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void immutableCarriersPreserveJsonFieldNamesAndRoundTripValues() throws Exception {
        assertRoundTrip(AccountDTO.class, "{\"id\":7,\"name\":\"Account\",\"type\":\"SAVINGS\",\"actualBalance\":12.34,\"creditLimit\":100,\"userId\":1,\"closingDate\":10,\"active\":true}");
        assertRoundTrip(BalanceDTO.class, "{\"userId\":1,\"balance\":12.34,\"date\":\"2026-01-01\",\"income\":20,\"expenses\":7.66,\"balanceDifference\":0}");
        assertRoundTrip(CdiRateDTO.class, "{\"currentRate\":13.25,\"lastUpdate\":\"2026-01-01\",\"source\":\"Banco Central do Brasil\"}");
        assertRoundTrip(FinancialGoalDTO.class, "{\"id\":7,\"userId\":1,\"name\":\"Reserve\",\"amount\":1000,\"description\":\"Goal\",\"status\":\"ACTIVE\",\"type\":\"SAVING\"}");
        assertRoundTrip(PaymentDTO.class, "{\"id\":7,\"transactionId\":1,\"accountId\":2,\"amount\":12.34,\"method\":\"PIX\",\"paidAt\":\"2026-01-01\",\"status\":\"COMPLETED\",\"userId\":1}");
        assertRoundTrip(MonthlyReportDTO.class, "{\"id\":1,\"month\":\"January\",\"income\":10.0,\"expenses\":5.0,\"balance\":5.0}");
        assertRoundTrip(DashboardSummaryDTO.class, "{\"id\":1,\"income\":10,\"expenses\":5,\"balance\":5}");
    }

    private <T> void assertRoundTrip(Class<T> type, String source) throws Exception {
        T dto = objectMapper.readValue(source, type);
        var encoded = objectMapper.readTree(objectMapper.writeValueAsString(dto));
        var expected = objectMapper.readTree(source);
        var fields = expected.properties().iterator();
        while (fields.hasNext()) {
            var field = fields.next();
            assertEquals(field.getValue(), encoded.get(field.getKey()), type.getSimpleName() + "." + field.getKey());
        }
        assertEquals(dto, objectMapper.treeToValue(encoded, type));
        assertEquals(type.getRecordComponents().length, encoded.size());
    }
}
