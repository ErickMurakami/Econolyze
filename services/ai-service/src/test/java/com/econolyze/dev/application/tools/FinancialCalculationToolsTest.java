package com.econolyze.dev.application.tools;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class FinancialCalculationToolsTest {
    @Test
    void futureBalanceIncludesEveryMonthlyDeposit() {
        var tools = new FinancialCalculationTools();

        var result = tools.simulateFutureBalance(new BigDecimal("100.50"), new BigDecimal("25.25"), 3);

        assertEquals("Saldo final: R$ 176.25", result);
    }

    @Test
    void zeroMonthsPreservesInitialBalance() {
        var tools = new FinancialCalculationTools();

        var result = tools.simulateFutureBalance(new BigDecimal("100.50"), BigDecimal.TEN, 0);

        assertEquals("Saldo final: R$ 100.50", result);
    }

    @Test
    void netBalancePreservesDeficitAndDecimalPrecision() {
        var tools = new FinancialCalculationTools();

        var result = tools.calculateNetBalance(new BigDecimal("10.10"), new BigDecimal("20.25"));

        assertEquals("Saldo líquido: R$ -10.15", result);
    }
}
