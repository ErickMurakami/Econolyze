package dev.econolyze.domain.entity;

import dev.econolyze.domain.enums.Category;
import dev.econolyze.domain.enums.PaymentMethod;
import dev.econolyze.domain.enums.RecurrenceFrequency;
import dev.econolyze.domain.enums.TransactionType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(schema = "finance", name = "recurrency_template")
public class RecurringTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;
    private String description;
    @Enumerated(EnumType.STRING)
    private RecurrenceFrequency frequency;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "max_occurrences")
    private Integer maxOccurrences;
    @Column(name = "next_occurrence")
    private LocalDate nextOccurrence;
    @Column(name = "times_processed")
    private Integer timesProcessed;
    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "recurringTemplateId")
    private List<Transaction> transactions;

    public Long getId() {
        return this.id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public TransactionType getType() {
        return this.type;
    }

    public Category getCategory() {
        return this.category;
    }

    public PaymentMethod getMethod() {
        return this.method;
    }

    public String getDescription() {
        return this.description;
    }

    public RecurrenceFrequency getFrequency() {
        return this.frequency;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Integer getMaxOccurrences() {
        return this.maxOccurrences;
    }

    public LocalDate getNextOccurrence() {
        return this.nextOccurrence;
    }

    public Integer getTimesProcessed() {
        return this.timesProcessed;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setType(final TransactionType type) {
        this.type = type;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public void setMethod(final PaymentMethod method) {
        this.method = method;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setFrequency(final RecurrenceFrequency frequency) {
        this.frequency = frequency;
    }

    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setMaxOccurrences(final Integer maxOccurrences) {
        this.maxOccurrences = maxOccurrences;
    }

    public void setNextOccurrence(final LocalDate nextOccurrence) {
        this.nextOccurrence = nextOccurrence;
    }

    public void setTimesProcessed(final Integer timesProcessed) {
        this.timesProcessed = timesProcessed;
    }

    public void setIsActive(final Boolean isActive) {
        this.isActive = isActive;
    }

    public void setTransactions(final List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public RecurringTemplate(
            final Long id,
            final Long userId,
            final BigDecimal amount,
            final TransactionType type,
            final Category category,
            final PaymentMethod method,
            final String description,
            final RecurrenceFrequency frequency,
            final LocalDate startDate,
            final LocalDate endDate,
            final Integer maxOccurrences,
            final LocalDate nextOccurrence,
            final Integer timesProcessed,
            final Boolean isActive,
            final List<Transaction> transactions
    ) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.method = method;
        this.description = description;
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxOccurrences = maxOccurrences;
        this.nextOccurrence = nextOccurrence;
        this.timesProcessed = timesProcessed;
        this.isActive = isActive;
        this.transactions = transactions;
    }

    public RecurringTemplate(
            Long userId,
            BigDecimal amount,
            TransactionType type,
            Category category,
            PaymentMethod method,
            String description,
            RecurrenceFrequency frequency,
            LocalDate startDate,
            LocalDate endDate,
            Integer maxOccurrences
    ) {
        this(
                null,
                userId,
                amount,
                type,
                category,
                method,
                description,
                frequency,
                startDate,
                endDate,
                maxOccurrences,
                startDate,
                0,
                true,
                null
        );
    }

    public RecurringTemplate() {
    }
}
