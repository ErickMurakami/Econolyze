package dev.econolyze.application.dto;

import java.math.BigDecimal;

public record AccountDTO(
        Long id,
        String name,
        String type,
        BigDecimal actualBalance,
        BigDecimal creditLimit,
        Long userId,
        Integer closingDate,
        Boolean active
) {
}
