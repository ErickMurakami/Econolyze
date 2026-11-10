package dev.econolyze.application.dto;

public record MonthlyReportDTO(
        Long id,
        String month,
        Double income,
        Double expenses,
        Double balance
) {
}
