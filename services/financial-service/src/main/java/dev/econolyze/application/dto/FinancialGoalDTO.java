package dev.econolyze.application.dto;

import dev.econolyze.domain.enums.GoalStatus;
import dev.econolyze.domain.enums.GoalType;

import java.math.BigDecimal;

public record FinancialGoalDTO(
        Long id,
        Long userId,
        String name,
        BigDecimal amount,
        String description,
        GoalType type,
        GoalStatus status
) {
}
