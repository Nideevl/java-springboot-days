package banking.payment;

import banking.core.AbstractPayment;
import banking.model.PaymentStatus;

public class NetBankingPayment extends AbstractPayment {
    private final String bankAccountNumber;
    private final String bankName;

    public NetBankingPayment(String senderId, double amount, String bankAccountNumber, String bankName) {
        super(senderId, amount);
        this.bankAccountNumber = bankAccountNumber;
        this.bankName = bankName;
    }

    @Override
    protected void executePayment() {
        System.out.println("Processing NetBanking payment for senderId "+getSenderId());
    }

    @Override
    public void refund() {
        System.out.println("Returning NetBanking payment "+getTransactionId());
        updateStatus(PaymentStatus.REFUNDED);
    }
}
