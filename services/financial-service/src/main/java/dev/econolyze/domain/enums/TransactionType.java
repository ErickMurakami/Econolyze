package dev.econolyze.domain.enums;

public enum TransactionType {
    INCOME( true, false),
    EXPENSE( false, true),
    TRANSFER( false, false),
    SAVINGS( false, false),
    REFUND( true, false),
    INVESTMENT( true, false);

    private final boolean increaseBalance;
    private final boolean decreaseBalance;

    TransactionType( boolean increaseBalance, boolean decreaseBalance) {
        this.increaseBalance = increaseBalance;
        this.decreaseBalance = decreaseBalance;
    }

    public boolean isIncreaseBalance() {
        return this.increaseBalance;
    }

    public boolean isDecreaseBalance() {
        return this.decreaseBalance;
    }
}
