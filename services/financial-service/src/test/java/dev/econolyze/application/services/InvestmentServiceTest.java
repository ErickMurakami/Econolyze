package dev.econolyze.application.services;

import dev.econolyze.application.dto.request.InvestmentProjectionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvestmentServiceTest {
    InvestmentService investmentService;
    CdiService cdiService;

    @BeforeEach
    void setUp() {
        cdiService = mock(CdiService.class);
        investmentService = new InvestmentService();
        investmentService.cdiService = cdiService;
    }

    @Test
    void calculateProjectionCompoundsAnnualRateOverTwelveMonths() {
        when(cdiService.getCurrentCdiRate()).thenReturn(new BigDecimal("12.00"));

        var response = investmentService.calculateProjection(
                new InvestmentProjectionRequest(
                        new BigDecimal("1000.00"),
                        12
                )).await().indefinitely();

        assertEquals(new BigDecimal("1120.00"), response.amountCdi());
        assertEquals(new BigDecimal("1000.00"), response.amountBlank());
    }

    @Test
    void calculateProjectionPreservesPrincipalForZeroMonths() {
        when(cdiService.getCurrentCdiRate()).thenReturn(new BigDecimal("12.00"));

        var response = investmentService.calculateProjection(
                new InvestmentProjectionRequest(
                        new BigDecimal("123.45"),
                        0
                )).await().indefinitely();

        assertEquals(new BigDecimal("123.45"), response.amountCdi());
    }

    @Test
    void calculateProjectionRejectsMissingAndInvalidInputsBeforeFetchingRate() {
        var requests = new InvestmentProjectionRequest[] {
                null,
                new InvestmentProjectionRequest(
                        null,
                        12
                ),
                new InvestmentProjectionRequest(
                        BigDecimal.ONE,
                        null
                ),
                new InvestmentProjectionRequest(
                        BigDecimal.ZERO,
                        12
                ),
                new InvestmentProjectionRequest(
                        new BigDecimal("-1"),
                        12
                ),
                new InvestmentProjectionRequest(
                        BigDecimal.ONE,
                        -1
                )
        };

        for (var request : requests) {
            assertThrows(IllegalArgumentException.class,
                    () -> investmentService.calculateProjection(request).await().indefinitely());
        }
        verifyNoInteractions(cdiService);
    }
}
