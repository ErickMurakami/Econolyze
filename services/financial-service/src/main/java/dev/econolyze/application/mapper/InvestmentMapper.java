package dev.econolyze.application.mapper;

import dev.econolyze.application.dto.InvestmentProjectionDTO;
import dev.econolyze.application.dto.response.InvestmentProjectionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = QuarkusMapperConfig.class)
public interface InvestmentMapper {
    InvestmentProjectionResponse mapToResponse(InvestmentProjectionDTO dto);
    @Mapping(target = "userId", ignore = true)
    InvestmentProjectionDTO mapToDTO(InvestmentProjectionResponse projection);
}
