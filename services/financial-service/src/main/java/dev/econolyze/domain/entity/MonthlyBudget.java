package dev.econolyze.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(schema = "finance", name = "monthly_budget")
public class MonthlyBudget {
    @Id
    private Long id;
    private BigDecimal amount;
    @Column(name = "user_id")
    private Long userId;

    public Long getId() {
        return this.id;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Long getUserId() {
        return this.userId;
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

    public MonthlyBudget(final Long id, final BigDecimal amount, final Long userId) {
        this.id = id;
        this.amount = amount;
        this.userId = userId;
    }

    public MonthlyBudget() {
    }
}
