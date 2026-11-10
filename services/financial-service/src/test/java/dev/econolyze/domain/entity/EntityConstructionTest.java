package dev.econolyze.domain.entity;

import dev.econolyze.domain.enums.PaymentMethod;
import dev.econolyze.domain.enums.PaymentStatus;
import dev.econolyze.domain.enums.RecurrenceFrequency;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EntityConstructionTest {
    @Test
    void paymentConstructorCreatesCompletedPaymentForSelectedAccount() {
        var transaction = new Transaction();
        var paidAt = LocalDate.of(2026, 1, 1);

        var payment = new Payment(
                transaction,
                7L,
                new BigDecimal("12.34"),
                PaymentMethod.PIX,
                paidAt
        );

        assertSame(transaction, payment.getTransaction());
        assertEquals(7L, payment.getAccountId());
        assertEquals(new BigDecimal("12.34"), payment.getAmount());
        assertEquals(PaymentStatus.COMPLETED, payment.getStatus());
        assertEquals(paidAt, payment.getPaidAt());
    }

    @Test
    void balanceConstructorStartsWithZeroTotals() {
        var date = LocalDate.of(2026, 1, 1);

        var balance = new Balance(
                1L,
                date
        );

        assertEquals(1L, balance.getUserId());
        assertEquals(date, balance.getDate());
        assertEquals(BigDecimal.ZERO, balance.getBalance());
        assertEquals(BigDecimal.ZERO, balance.getIncome());
        assertEquals(BigDecimal.ZERO, balance.getExpenses());
        assertNull(balance.getVersion());
    }

    @Test
    void recurringConstructorStartsActiveAndUnprocessedAtStartDate() {
        var start = LocalDate.of(2026, 1, 1);

        var template = new RecurringTemplate(
                1L,
                BigDecimal.TEN,
                null,
                null,
                PaymentMethod.PIX,
                "Subscription",
                RecurrenceFrequency.MONTHLY,
                start,
                null,
                12
        );

        assertTrue(template.getIsActive());
        assertEquals(0, template.getTimesProcessed());
        assertEquals(start, template.getNextOccurrence());
        assertEquals(12, template.getMaxOccurrences());
        assertNull(template.getId());
    }
}
