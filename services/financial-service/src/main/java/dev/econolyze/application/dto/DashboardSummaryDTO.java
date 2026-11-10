package dev.econolyze.application.dto;

public record DashboardSummaryDTO(
        Long id,
        Long income,
        Long expenses,
        Long balance
) {
}
