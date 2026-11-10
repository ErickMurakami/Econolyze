package dev.econolyze.application.services;

import dev.econolyze.infrastructure.config.BancoCentralConfig;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CdiServiceTest {
    CdiService cdiService;
    BancoCentralConfig bancoCentralConfig;

    @BeforeEach
    void setUp() {
        bancoCentralConfig = mock(BancoCentralConfig.class);
        cdiService = new CdiService();
        cdiService.bancoCentralConfig = bancoCentralConfig;
    }

    @Test
    void asyncRateFallsBackToCachedRateWhenBankIsUnavailable() {
        when(bancoCentralConfig.getLatestCdiRate(1, "json"))
                .thenReturn(Uni.createFrom().failure(new IllegalStateException("Unavailable")));

        var rate = cdiService.getCurrentCdiRateAsync().await().indefinitely();

        assertEquals(cdiService.getCurrentCdiRate(), rate);
    }

    @Test
    void asyncRateParsesBankResponse() {
        when(bancoCentralConfig.getLatestCdiRate(1, "json"))
                .thenReturn(Uni.createFrom().item(List.of(Map.<String, Object>of("valor", "13.25"))));

        var rate = cdiService.getCurrentCdiRateAsync().await().indefinitely();

        assertEquals(new BigDecimal("13.25"), rate);
    }

    @Test
    void emptyBankResponseKeepsCachedRate() {
        when(bancoCentralConfig.getLatestCdiRate(1, "json"))
                .thenReturn(Uni.createFrom().item(List.of()));

        var rate = cdiService.getCurrentCdiRateAsync().await().indefinitely();

        assertEquals(cdiService.getCurrentCdiRate(), rate);
    }
}
