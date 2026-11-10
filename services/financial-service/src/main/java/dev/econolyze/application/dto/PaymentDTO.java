package dev.econolyze.application.dto;

import dev.econolyze.domain.enums.PaymentMethod;
import dev.econolyze.domain.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentDTO(
        Long id,
        Long transactionId,
        BigDecimal amount,
        PaymentMethod method,
        LocalDate paidAt,
        PaymentStatus status,
        Long accountId,
        Long userId
) {
}
