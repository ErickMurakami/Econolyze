package dev.econolyze.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CdiRateDTO(
        BigDecimal currentRate,
        LocalDate lastUpdate,
        String source
) {
}
