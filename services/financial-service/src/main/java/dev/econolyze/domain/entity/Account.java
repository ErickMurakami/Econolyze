package dev.econolyze.domain.entity;

import dev.econolyze.domain.enums.AccountType;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(schema = "finance", name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    private String name;
    @Enumerated(EnumType.STRING)
    private AccountType type;
    @Column(name = "actual_balance")
    private BigDecimal actualBalance;
    @Column(name = "credit_limit")
    private BigDecimal creditLimit;
    @Column(name = "closing_date")
    private Integer closingDate;
    private Boolean active;

    public Long getId() {
        return this.id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getName() {
        return this.name;
    }

    public AccountType getType() {
        return this.type;
    }

    public BigDecimal getActualBalance() {
        return this.actualBalance;
    }

    public BigDecimal getCreditLimit() {
        return this.creditLimit;
    }

    public Integer getClosingDate() {
        return this.closingDate;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setType(final AccountType type) {
        this.type = type;
    }

    public void setActualBalance(final BigDecimal actualBalance) {
        this.actualBalance = actualBalance;
    }

    public void setCreditLimit(final BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setClosingDate(final Integer closingDate) {
        this.closingDate = closingDate;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public Account(final Long id, final Long userId, final String name, final AccountType type, final BigDecimal actualBalance, final BigDecimal creditLimit, final Integer closingDate, final Boolean active) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.actualBalance = actualBalance;
        this.creditLimit = creditLimit;
        this.closingDate = closingDate;
        this.active = active;
    }

    public Account() {
    }
}
