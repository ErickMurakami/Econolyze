package dev.econolyze.domain.entity;

import dev.econolyze.domain.enums.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(schema = "finance", name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "financial_goal_id")
    private Long financialGoalId;
    @Column(name = "account_id")
    private Long accountId;
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TransactionType type;
    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private PaymentMethod method;
    @Column(name = "recurring_template_id")
    private Long recurringTemplateId;
    @Column(name = "is_recurring")
    private Boolean isRecurring;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status = TransactionStatus.PENDING;
    private String description;
    private LocalDate date;
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Payment> payments = new HashSet<>();

    @Transient
    private BigDecimal cachedTotalPaid;

    public BigDecimal getPaidAmount() {
        if (cachedTotalPaid != null) return cachedTotalPaid;

        if (payments == null || payments.isEmpty()) {
            cachedTotalPaid = BigDecimal.ZERO;
            return cachedTotalPaid;
        }

        cachedTotalPaid = payments.stream()
                .filter(p -> p.getStatus() == PaymentStatus.COMPLETED)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return cachedTotalPaid;
    }

    public BigDecimal getRemainingBalance() {
        return amount.subtract(getPaidAmount());
    }

    public void recalculateStatus() {

        if (status == TransactionStatus.CANCELLED)
            return;

        BigDecimal totalPaid = payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalPaid.compareTo(BigDecimal.ZERO) == 0) {
            status = TransactionStatus.PENDING;
        } else if (totalPaid.compareTo(amount) >= 0) {
            status = TransactionStatus.PAID;
        } else {
            status = TransactionStatus.PAID_PARTIALLY;
        }
    }

    @PrePersist
    @PreUpdate
    public void onSave(){
        if (status == null)
            status = TransactionStatus.PENDING;
    }

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

    public Set<Payment> getPayments() {
        return this.payments;
    }

    public BigDecimal getCachedTotalPaid() {
        return this.cachedTotalPaid;
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

    public void setPayments(final Set<Payment> payments) {
        this.payments = payments;
    }

    public void setCachedTotalPaid(final BigDecimal cachedTotalPaid) {
        this.cachedTotalPaid = cachedTotalPaid;
    }

    public Transaction(final Long id, final BigDecimal amount, final Long userId, final Long financialGoalId, final Long accountId, final Category category, final TransactionType type, final PaymentMethod method, final Long recurringTemplateId, final Boolean isRecurring, final TransactionStatus status, final String description, final LocalDate date, final Set<Payment> payments, final BigDecimal cachedTotalPaid) {
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
        this.cachedTotalPaid = cachedTotalPaid;
    }

    public Transaction(RecurringTemplate template) {
        this(null, template.getAmount(), template.getUserId(), null, null, template.getCategory(),
                template.getType(), null, template.getId(), true, null,
                template.getDescription() + " (Recorrente)", template.getNextOccurrence(), null, null);
    }

    public Transaction() {
    }
}
