package dev.econolyze.application.dto;

import dev.econolyze.domain.enums.Category;
import dev.econolyze.domain.enums.PaymentMethod;
import dev.econolyze.domain.enums.TransactionStatus;
import dev.econolyze.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDTO {
    private Long id;
    private BigDecimal amount;
    private Long userId;
    private Long financialGoalId;
    private Long accountId;
    private Category category;
    private TransactionType type;
    private PaymentMethod method;
    private Long recurringTemplateId;
    private Boolean isRecurring;
    private TransactionStatus status;
    private String description;
    private LocalDate date;
    private List<PaymentDTO> payments = new ArrayList<>();

    public Long getId() {
        return this.id;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Long getFinancialGoalId() {
        return this.financialGoalId;
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public Category getCategory() {
        return this.category;
    }

    public TransactionType getType() {
        return this.type;
    }

    public PaymentMethod getMethod() {
        return this.method;
    }

    public Long getRecurringTemplateId() {
        return this.recurringTemplateId;
    }

    public Boolean getIsRecurring() {
        return this.isRecurring;
    }

    public TransactionStatus getStatus() {
        return this.status;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public List<PaymentDTO> getPayments() {
        return this.payments;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public void setFinancialGoalId(final Long financialGoalId) {
        this.financialGoalId = financialGoalId;
    }

    public void setAccountId(final Long accountId) {
        this.accountId = accountId;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public void setType(final TransactionType type) {
        this.type = type;
    }

    public void setMethod(final PaymentMethod method) {
        this.method = method;
    }

    public void setRecurringTemplateId(final Long recurringTemplateId) {
        this.recurringTemplateId = recurringTemplateId;
    }

    public void setIsRecurring(final Boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    public void setStatus(final TransactionStatus status) {
        this.status = status;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public void setPayments(final List<PaymentDTO> payments) {
        this.payments = payments;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TransactionDTO)) return false;
        final TransactionDTO other = (TransactionDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$financialGoalId = this.getFinancialGoalId();
        final Object other$financialGoalId = other.getFinancialGoalId();
        if (this$financialGoalId == null ? other$financialGoalId != null : !this$financialGoalId.equals(other$financialGoalId)) return false;
        final Object this$accountId = this.getAccountId();
        final Object other$accountId = other.getAccountId();
        if (this$accountId == null ? other$accountId != null : !this$accountId.equals(other$accountId)) return false;
        final Object this$recurringTemplateId = this.getRecurringTemplateId();
        final Object other$recurringTemplateId = other.getRecurringTemplateId();
        if (this$recurringTemplateId == null ? other$recurringTemplateId != null : !this$recurringTemplateId.equals(other$recurringTemplateId)) return false;
        final Object this$isRecurring = this.getIsRecurring();
        final Object other$isRecurring = other.getIsRecurring();
        if (this$isRecurring == null ? other$isRecurring != null : !this$isRecurring.equals(other$isRecurring)) return false;
        final Object this$amount = this.getAmount();
        final Object other$amount = other.getAmount();
        if (this$amount == null ? other$amount != null : !this$amount.equals(other$amount)) return false;
        final Object this$category = this.getCategory();
        final Object other$category = other.getCategory();
        if (this$category == null ? other$category != null : !this$category.equals(other$category)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$method = this.getMethod();
        final Object other$method = other.getMethod();
        if (this$method == null ? other$method != null : !this$method.equals(other$method)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) return false;
        final Object this$date = this.getDate();
        final Object other$date = other.getDate();
        if (this$date == null ? other$date != null : !this$date.equals(other$date)) return false;
        final Object this$payments = this.getPayments();
        final Object other$payments = other.getPayments();
        if (this$payments == null ? other$payments != null : !this$payments.equals(other$payments)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TransactionDTO;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $financialGoalId = this.getFinancialGoalId();
        result = result * PRIME + ($financialGoalId == null ? 43 : $financialGoalId.hashCode());
        final Object $accountId = this.getAccountId();
        result = result * PRIME + ($accountId == null ? 43 : $accountId.hashCode());
        final Object $recurringTemplateId = this.getRecurringTemplateId();
        result = result * PRIME + ($recurringTemplateId == null ? 43 : $recurringTemplateId.hashCode());
        final Object $isRecurring = this.getIsRecurring();
        result = result * PRIME + ($isRecurring == null ? 43 : $isRecurring.hashCode());
        final Object $amount = this.getAmount();
        result = result * PRIME + ($amount == null ? 43 : $amount.hashCode());
        final Object $category = this.getCategory();
        result = result * PRIME + ($category == null ? 43 : $category.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $method = this.getMethod();
        result = result * PRIME + ($method == null ? 43 : $method.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $date = this.getDate();
        result = result * PRIME + ($date == null ? 43 : $date.hashCode());
        final Object $payments = this.getPayments();
        result = result * PRIME + ($payments == null ? 43 : $payments.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "TransactionDTO(id=" + this.getId() + ", amount=" + this.getAmount() + ", userId=" + this.getUserId() + ", financialGoalId=" + this.getFinancialGoalId() + ", accountId=" + this.getAccountId() + ", category=" + this.getCategory() + ", type=" + this.getType() + ", method=" + this.getMethod() + ", recurringTemplateId=" + this.getRecurringTemplateId() + ", isRecurring=" + this.getIsRecurring() + ", status=" + this.getStatus() + ", description=" + this.getDescription() + ", date=" + this.getDate() + ", payments=" + this.getPayments() + ")";
    }

    public TransactionDTO(final Long id, final BigDecimal amount, final Long userId, final Long financialGoalId, final Long accountId, final Category category, final TransactionType type, final PaymentMethod method, final Long recurringTemplateId, final Boolean isRecurring, final TransactionStatus status, final String description, final LocalDate date, final List<PaymentDTO> payments) {
        this.id = id;
        this.amount = amount;
        this.userId = userId;
        this.financialGoalId = financialGoalId;
        this.accountId = accountId;
        this.category = category;
        this.type = type;
        this.method = method;
        this.recurringTemplateId = recurringTemplateId;
        this.isRecurring = isRecurring;
        this.status = status;
        this.description = description;
        this.date = date;
        this.payments = payments;
    }

    public TransactionDTO() {
    }
}
