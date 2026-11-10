package dev.econolyze.application.services;

import dev.econolyze.application.dto.FinancialGoalDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AnalyticsServiceTest {
    @Test
    void analyzeGoalProgressPreservesFractionalPercentageAndGoalIdentity() {
        var goal = new FinancialGoalDTO(
                7L,
                null,
                "Reserve",
                new BigDecimal("300.00"),
                null,
                null,
                null
        );

        var progress = new AnalyticsService().analyzeGoalProgress(goal, new BigDecimal("100.00"));

        assertEquals(7L, progress.getId());
        assertEquals("Reserve", progress.getName());
        assertEquals(33.33333333, progress.getProgress(), 0.00000001);
    }

    @Test
    void analyzeGoalProgressAllowsProgressBeyondTarget() {
        var goal = new FinancialGoalDTO(
                null,
                null,
                null,
                new BigDecimal("100.00"),
                null,
                null,
                null
        );

        var progress = new AnalyticsService().analyzeGoalProgress(goal, new BigDecimal("125.00"));

        assertEquals(125.0, progress.getProgress());
    }
}
