package dev.econolyze.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InvestmentProjectionDTO {
    private Long userId;
    private BigDecimal amountBlank;
    private BigDecimal amountCdi;
    private String category;
    private String description;
    private LocalDate date;

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public void setAmountBlank(final BigDecimal amountBlank) {
        this.amountBlank = amountBlank;
    }

    public void setAmountCdi(final BigDecimal amountCdi) {
        this.amountCdi = amountCdi;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof InvestmentProjectionDTO)) return false;
        final InvestmentProjectionDTO other = (InvestmentProjectionDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$amountBlank = this.getAmountBlank();
        final Object other$amountBlank = other.getAmountBlank();
        if (this$amountBlank == null ? other$amountBlank != null : !this$amountBlank.equals(other$amountBlank)) return false;
        final Object this$amountCdi = this.getAmountCdi();
        final Object other$amountCdi = other.getAmountCdi();
        if (this$amountCdi == null ? other$amountCdi != null : !this$amountCdi.equals(other$amountCdi)) return false;
        final Object this$category = this.getCategory();
        final Object other$category = other.getCategory();
        if (this$category == null ? other$category != null : !this$category.equals(other$category)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) return false;
        final Object this$date = this.getDate();
        final Object other$date = other.getDate();
        if (this$date == null ? other$date != null : !this$date.equals(other$date)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof InvestmentProjectionDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $amountBlank = this.getAmountBlank();
        result = result * PRIME + ($amountBlank == null ? 43 : $amountBlank.hashCode());
        final Object $amountCdi = this.getAmountCdi();
        result = result * PRIME + ($amountCdi == null ? 43 : $amountCdi.hashCode());
        final Object $category = this.getCategory();
        result = result * PRIME + ($category == null ? 43 : $category.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $date = this.getDate();
        result = result * PRIME + ($date == null ? 43 : $date.hashCode());
        return result;
    }

    public String toString() {
        return "InvestmentProjectionDTO(userId=" + this.getUserId() + ", amountBlank=" + this.getAmountBlank() + ", amountCdi=" + this.getAmountCdi() + ", category=" + this.getCategory() + ", description=" + this.getDescription() + ", date=" + this.getDate() + ")";
    }

    public Long getUserId() {
        return this.userId;
    }

    public BigDecimal getAmountBlank() {
        return this.amountBlank;
    }

    public BigDecimal getAmountCdi() {
        return this.amountCdi;
    }

    public String getCategory() {
        return this.category;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public InvestmentProjectionDTO() {
    }

    public InvestmentProjectionDTO(final Long userId, final BigDecimal amountBlank, final BigDecimal amountCdi, final String category, final String description, final LocalDate date) {
        this.userId = userId;
        this.amountBlank = amountBlank;
        this.amountCdi = amountCdi;
        this.category = category;
        this.description = description;
        this.date = date;
    }
}
