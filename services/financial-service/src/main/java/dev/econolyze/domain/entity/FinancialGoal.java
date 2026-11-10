package dev.econolyze.domain.entity;

import dev.econolyze.domain.enums.GoalStatus;
import dev.econolyze.domain.enums.GoalType;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(schema = "finance", name = "financial_goal")
public class FinancialGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    private String name;
    private BigDecimal amount;
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private GoalStatus status;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private GoalType type;

    public Long getId() {
        return this.id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public String getDescription() {
        return this.description;
    }

    public GoalStatus getStatus() {
        return this.status;
    }

    public GoalType getType() {
        return this.type;
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

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setStatus(final GoalStatus status) {
        this.status = status;
    }

    public void setType(final GoalType type) {
        this.type = type;
    }

    public FinancialGoal(
            final Long id,
            final Long userId,
            final String name,
            final BigDecimal amount,
            final String description,
            final GoalStatus status,
            final GoalType type
    ) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.type = type;
    }

    public FinancialGoal() {
    }
}
