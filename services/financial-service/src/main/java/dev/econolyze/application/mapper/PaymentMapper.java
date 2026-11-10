package dev.econolyze.application.mapper;

import dev.econolyze.application.dto.PaymentDTO;
import dev.econolyze.application.dto.request.PaymentRequest;
import dev.econolyze.application.dto.response.PaymentResponse;
import dev.econolyze.domain.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = QuarkusMapperConfig.class)
public interface PaymentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "transaction", ignore = true)
    @Mapping(target = "description", ignore = true)
    Payment mapToEntity(PaymentRequest request);
    @Mapping(target = "transactionId", source = "transaction.id")
    PaymentResponse mapToResponse(Payment payment);
    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    PaymentDTO mapToDto(Payment payment);

    @Mapping(target = "transaction", ignore = true)
    @Mapping(target = "description", ignore = true)
    Payment mapToEntity(PaymentDTO paymentDTO);
}
