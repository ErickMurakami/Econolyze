package dev.econolyze.domain.entity;

import dev.econolyze.domain.enums.PaymentMethod;
import dev.econolyze.domain.enums.PaymentStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(schema = "finance", name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transaction_id", foreignKey = @ForeignKey(name = "fk_payments_transaction_id"))
    private Transaction transaction;
    @Column(name = "account_id")
    private Long accountId;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;
    @Column(name = "paid_at")
    private LocalDate paidAt = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.COMPLETED;
    private String description;

    @PrePersist
    @PreUpdate
    public void updateTransaction(){
        if (transaction != null){
            transaction.recalculateStatus();
        }
        if(paidAt == null){
            paidAt = LocalDate.now();
        }
        if(status == null){
            status = PaymentStatus.COMPLETED;
        }
    }

    public Long getId() {
        return this.id;
    }

    public Transaction getTransaction() {
        return this.transaction;
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public PaymentMethod getMethod() {
        return this.method;
    }

    public LocalDate getPaidAt() {
        return this.paidAt;
    }

    public PaymentStatus getStatus() {
        return this.status;
    }

    public String getDescription() {
        return this.description;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setTransaction(final Transaction transaction) {
        this.transaction = transaction;
    }

    public void setAccountId(final Long accountId) {
        this.accountId = accountId;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setMethod(final PaymentMethod method) {
        this.method = method;
    }

    public void setPaidAt(final LocalDate paidAt) {
        this.paidAt = paidAt;
    }

    public void setStatus(final PaymentStatus status) {
        this.status = status;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Payment(
            final Long id,
            final Transaction transaction,
            final Long accountId,
            final BigDecimal amount,
            final PaymentMethod method,
            final LocalDate paidAt,
            final PaymentStatus status,
            final String description
    ) {
        this.id = id;
        this.transaction = transaction;
        this.accountId = accountId;
        this.amount = amount;
        this.method = method;
        this.paidAt = paidAt;
        this.status = status;
        this.description = description;
    }

    public Payment(
            Transaction transaction,
            Long accountId,
            BigDecimal amount,
            PaymentMethod method,
            LocalDate paidAt
    ) {
        this(
                null,
                transaction,
                accountId,
                amount,
                method,
                paidAt,
                PaymentStatus.COMPLETED,
                null
        );
    }

    public Payment() {
    }
}
