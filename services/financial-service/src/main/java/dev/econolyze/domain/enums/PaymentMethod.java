package dev.econolyze.domain.enums;

public enum PaymentMethod {
    CREDIT_CARD(1, true),
    CASH(2, true),
    BANK_TRANSFER(3, false),
    PAYPAL(4, true),
    PIX(5, true),
    DEBIT_CARD(6, true);

    public final int code;
    public final boolean instantDebit;

    PaymentMethod(
            int code,
            boolean instantDebit
    ){
        this.code = code;
        this.instantDebit = instantDebit;
    }

    public static PaymentMethod fromCode(int code){
        for(PaymentMethod p : PaymentMethod.values()){
            if(p.code == code){
                return p;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

    public int getCode() {
        return this.code;
    }

    public boolean isInstantDebit() {
        return this.instantDebit;
    }
}
