package dev.econolyze.application.mapper;

import dev.econolyze.application.dto.request.CreateAccountRequest;
import dev.econolyze.application.dto.request.UpdateAccountRequest;
import dev.econolyze.application.dto.response.AccountResponse;
import dev.econolyze.application.dto.AccountDTO;
import dev.econolyze.domain.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = QuarkusMapperConfig.class)
public interface AccountMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    Account mapToEntity(CreateAccountRequest request);
    @Mapping(target = "userId", ignore = true)
    Account mapToEntity(UpdateAccountRequest request);
    AccountResponse mapToResponse(Account account);
    AccountDTO mapToDTO(Account account);
}
