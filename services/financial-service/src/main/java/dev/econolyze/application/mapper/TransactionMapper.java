package dev.econolyze.application.mapper;

import dev.econolyze.application.dto.TransactionDTO;
import dev.econolyze.application.dto.request.TransactionRequest;
import dev.econolyze.application.dto.response.TransactionResponse;
import dev.econolyze.domain.entity.Transaction;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.HashSet;

@Mapper(config = QuarkusMapperConfig.class, uses = PaymentMapper.class)
public interface TransactionMapper {

    @Mapping(target = "cachedTotalPaid", ignore = true)
    Transaction mapToEntity(TransactionDTO transactionDTO);

    TransactionDTO mapToDTO(Transaction transaction);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "payments", ignore = true)
    TransactionDTO mapToDTO(TransactionRequest transactionRequest);

    @Mapping(target = "initialPayment", ignore = true)
    TransactionResponse mapToResponse(Transaction transaction);

    @AfterMapping
    default void ensurePaymentsInitialized(@MappingTarget Transaction transaction) {
        if (transaction.getPayments() == null) {
            transaction.setPayments(new HashSet<>());
        }
    }
}