package dev.econolyze.domain.entity;

import dev.econolyze.domain.enums.PaymentStatus;
import dev.econolyze.domain.enums.TransactionStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    @Test
    void paidAmountIncludesOnlyCompletedPayments() {
        var transaction = new Transaction();
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.setPayments(new HashSet<>(Set.of(
                payment("20.10", PaymentStatus.COMPLETED),
                payment("10.20", PaymentStatus.COMPLETED),
                payment("50.00", PaymentStatus.PENDING),
                payment("5.00", PaymentStatus.FAILED),
                payment("3.00", PaymentStatus.REFUNDED))));

        assertEquals(new BigDecimal("30.30"), transaction.getPaidAmount());
        assertEquals(new BigDecimal("69.70"), transaction.getRemainingBalance());
    }

    @Test
    void completedPaymentsMoveTransactionThroughPartialAndPaidStates() {
        var transaction = new Transaction();
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.recalculateStatus();
        assertEquals(TransactionStatus.PENDING, transaction.getStatus());

        transaction.getPayments().add(payment("40.00", PaymentStatus.COMPLETED));
        transaction.recalculateStatus();
        assertEquals(TransactionStatus.PAID_PARTIALLY, transaction.getStatus());

        transaction.getPayments().add(payment("60.00", PaymentStatus.COMPLETED));
        transaction.recalculateStatus();
        assertEquals(TransactionStatus.PAID, transaction.getStatus());
    }

    @Test
    void cancelledTransactionKeepsItsStatusWhenPaymentsChange() {
        var transaction = new Transaction();
        transaction.setAmount(BigDecimal.TEN);
        transaction.setStatus(TransactionStatus.CANCELLED);
        transaction.getPayments().add(payment("10.00", PaymentStatus.COMPLETED));

        transaction.recalculateStatus();

        assertEquals(TransactionStatus.CANCELLED, transaction.getStatus());
    }

    @Test
    void noArgumentConstructorInitializesPendingTransactionWithNoPayments() {
        var transaction = new Transaction();

        assertNotNull(transaction.getPayments());
        assertEquals(TransactionStatus.PENDING, transaction.getStatus());
    }

    @Test
    void recurringConstructorCopiesTemplateWithoutChangingLifecycleDefaults() {
        var template = new RecurringTemplate();
        template.setId(7L);
        template.setUserId(1L);
        template.setAmount(new BigDecimal("25.50"));
        template.setDescription("Subscription");
        template.setNextOccurrence(LocalDate.of(2026, 1, 1));

        var transaction = new Transaction(template);

        assertEquals(1L, transaction.getUserId());
        assertEquals(7L, transaction.getRecurringTemplateId());
        assertEquals(new BigDecimal("25.50"), transaction.getAmount());
        assertEquals("Subscription (Recorrente)", transaction.getDescription());
        assertEquals(template.getNextOccurrence(), transaction.getDate());
        assertTrue(transaction.getIsRecurring());
        assertNull(transaction.getPayments());
        assertNull(transaction.getStatus());
        transaction.onSave();
        assertEquals(TransactionStatus.PENDING, transaction.getStatus());
    }

    private Payment payment(String amount, PaymentStatus status) {
        var payment = new Payment();
        payment.setAmount(new BigDecimal(amount));
        payment.setStatus(status);
        return payment;
    }
}
