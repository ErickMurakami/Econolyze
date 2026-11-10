package dev.econolyze.application.services;

import dev.econolyze.application.dto.request.PaymentRequest;
import dev.econolyze.application.security.UserContext;
import dev.econolyze.domain.entity.Transaction;
import dev.econolyze.domain.enums.TransactionStatus;
import dev.econolyze.infrastructure.repository.PaymentRepository;
import dev.econolyze.infrastructure.repository.TransactionRepository;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {
    PaymentService paymentService;
    TransactionRepository transactionRepository;
    PaymentRepository paymentRepository;
    UserContext userContext;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        paymentRepository = mock(PaymentRepository.class);
        userContext = mock(UserContext.class);
        paymentService = new PaymentService();
        paymentService.transactionRepository = transactionRepository;
        paymentService.paymentRepository = paymentRepository;
        paymentService.userContext = userContext;
    }

    @Test
    void missingTransactionFailsWithoutPersistingPayment() {
        when(transactionRepository.findTransactionById(7L)).thenReturn(Uni.createFrom().nullItem());

        var exception = assertThrows(RuntimeException.class,
                () -> paymentService.createPayment(request("10.00")).await().indefinitely());

        assertEquals("Transaction not found", exception.getMessage());
        verifyNoInteractions(paymentRepository);
    }

    @Test
    void anotherUsersTransactionRejectsPayment() {
        var transaction = transaction();
        when(userContext.getUserId()).thenReturn(2L);
        when(transactionRepository.findTransactionById(7L)).thenReturn(Uni.createFrom().item(transaction));

        assertThrows(SecurityException.class,
                () -> paymentService.createPayment(request("10.00")).await().indefinitely());

        verifyNoInteractions(paymentRepository);
    }

    @Test
    void paidTransactionRejectsAdditionalPayment() {
        var transaction = transaction();
        transaction.setStatus(TransactionStatus.PAID);
        when(userContext.getUserId()).thenReturn(1L);
        when(transactionRepository.findTransactionById(7L)).thenReturn(Uni.createFrom().item(transaction));

        var exception = assertThrows(RuntimeException.class,
                () -> paymentService.createPayment(request("10.00")).await().indefinitely());

        assertEquals("Transaction is already paid", exception.getMessage());
        verifyNoInteractions(paymentRepository);
    }

    @Test
    void amountExceedingRemainingBalanceRejectsPayment() {
        when(userContext.getUserId()).thenReturn(1L);
        when(transactionRepository.findTransactionById(7L)).thenReturn(Uni.createFrom().item(transaction()));

        var exception = assertThrows(RuntimeException.class,
                () -> paymentService.createPayment(request("100.01")).await().indefinitely());

        assertEquals("Payment amount exceeds remaining balance", exception.getMessage());
        verifyNoInteractions(paymentRepository);
    }

    private Transaction transaction() {
        var transaction = new Transaction();
        transaction.setUserId(1L);
        transaction.setAmount(new BigDecimal("100.00"));
        return transaction;
    }

    private PaymentRequest request(String amount) {
        return new PaymentRequest(7L, new BigDecimal(amount), null, null, null, null);
    }
}
