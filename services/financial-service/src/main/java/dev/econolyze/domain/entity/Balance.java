package dev.econolyze.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(schema = "finance", name = "balance")
public class Balance {
    @Id
    @Column(name = "user_id")
    private Long userId;
    private BigDecimal balance;
    private LocalDate date;
    private BigDecimal income;
    private BigDecimal expenses;
    @Version
    public Long version;

    public Long getUserId() {
        return this.userId;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public BigDecimal getIncome() {
        return this.income;
    }

    public BigDecimal getExpenses() {
        return this.expenses;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public void setBalance(final BigDecimal balance) {
        this.balance = balance;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public void setIncome(final BigDecimal income) {
        this.income = income;
    }

    public void setExpenses(final BigDecimal expenses) {
        this.expenses = expenses;
    }

    public void setVersion(final Long version) {
        this.version = version;
    }

    public Balance(final Long userId, final BigDecimal balance, final LocalDate date, final BigDecimal income, final BigDecimal expenses, final Long version) {
        this.userId = userId;
        this.balance = balance;
        this.date = date;
        this.income = income;
        this.expenses = expenses;
        this.version = version;
    }

    public Balance(Long userId, LocalDate date) {
        this(userId, BigDecimal.ZERO, date, BigDecimal.ZERO, BigDecimal.ZERO, null);
    }

    public Balance() {
    }
}
