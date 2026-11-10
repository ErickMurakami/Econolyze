package dev.econolyze.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BalanceDTO(
        Long userId,
        BigDecimal balance,
        LocalDate date,
        BigDecimal income,
        BigDecimal expenses,
        BigDecimal balanceDifference
) {
}
