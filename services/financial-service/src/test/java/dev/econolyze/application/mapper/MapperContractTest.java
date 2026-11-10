package dev.econolyze.application.mapper;

import dev.econolyze.application.dto.request.CreateAccountRequest;
import dev.econolyze.application.dto.request.UpdateAccountRequest;
import dev.econolyze.domain.entity.Payment;
import dev.econolyze.domain.enums.AccountType;
import dev.econolyze.domain.enums.PaymentMethod;
import dev.econolyze.domain.enums.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MapperContractTest {
    AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);
    PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);

    @Test
    void createAccountMapsValuesAndLeavesIdentityForPersistenceAndService() {
        var request = new CreateAccountRequest(
                "Reserve",
                AccountType.SAVINGS_ACCOUNT,
                new BigDecimal("123.4567"),
                BigDecimal.ZERO,
                10,
                true
        );

        var account = accountMapper.mapToEntity(request);
        var dto = accountMapper.mapToDTO(account);

        assertNull(account.getId());
        assertNull(account.getUserId());
        assertEquals("Reserve", dto.name());
        assertEquals("SAVINGS_ACCOUNT", dto.type());
        assertEquals(new BigDecimal("123.4567"), dto.actualBalance());
        assertEquals(10, dto.closingDate());
        assertTrue(dto.active());
    }

    @Test
    void updateAccountPreservesRequestIdentityWithoutAssigningOwner() {
        var request = new UpdateAccountRequest(
                7L,
                "Account",
                AccountType.CHECKING_ACCOUNT,
                BigDecimal.TEN,
                null,
                null,
                false
        );

        var account = accountMapper.mapToEntity(request);

        assertEquals(7L, account.getId());
        assertNull(account.getUserId());
        assertEquals(BigDecimal.TEN, account.getActualBalance());
        assertFalse(account.getActive());
    }

    @Test
    void paymentDtoRoundTripPreservesAmountAndExistingUnmappedRelationshipContract() {
        var payment = new Payment(
                null,
                7L,
                new BigDecimal("12.3456"),
                PaymentMethod.PIX,
                LocalDate.of(2026, 1, 1)
        );
        payment.setId(9L);

        var dto = paymentMapper.mapToDto(payment);
        var mapped = paymentMapper.mapToEntity(dto);

        assertNull(dto.transactionId());
        assertNull(dto.userId());
        assertEquals(9L, mapped.getId());
        assertEquals(7L, mapped.getAccountId());
        assertEquals(new BigDecimal("12.3456"), mapped.getAmount());
        assertEquals(PaymentStatus.COMPLETED, mapped.getStatus());
        assertEquals(payment.getPaidAt(), mapped.getPaidAt());
        assertNull(mapped.getTransaction());
        assertNull(mapped.getDescription());
    }
}
