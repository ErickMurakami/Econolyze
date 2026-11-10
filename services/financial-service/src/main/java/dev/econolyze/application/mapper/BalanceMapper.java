package dev.econolyze.application.mapper;

import dev.econolyze.application.dto.BalanceDTO;
import dev.econolyze.domain.entity.Balance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = QuarkusMapperConfig.class)
public interface BalanceMapper {
    @Mapping(target = "balanceDifference", ignore = true)
    BalanceDTO mapToDTO(Balance balance);
}
