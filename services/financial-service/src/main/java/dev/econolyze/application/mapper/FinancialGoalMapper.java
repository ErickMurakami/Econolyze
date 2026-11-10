package dev.econolyze.application.mapper;

import dev.econolyze.application.dto.FinancialGoalDTO;
import dev.econolyze.application.dto.response.FinancialGoalResponse;
import dev.econolyze.domain.entity.FinancialGoal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = QuarkusMapperConfig.class)
public interface FinancialGoalMapper {
    FinancialGoalDTO mapToDTO(FinancialGoal financialGoal);
    @Mapping(target = "userId", ignore = true)
    FinancialGoalDTO mapToDTO(FinancialGoalResponse financialGoalResponse);
    FinancialGoal mapToEntity(FinancialGoalDTO financialGoalDTO);
    FinancialGoalResponse mapToResponse(FinancialGoal financialGoal);
}
