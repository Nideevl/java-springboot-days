package banking.payment;

import banking.core.AbstractPayment;
import banking.model.PaymentStatus;

public class UPIPayment extends AbstractPayment {
    private final String upiId;

    public UPIPayment(String senderId, double amount, String upiId){
        super(senderId, amount);
        this.upiId = upiId;
    }

    @Override
    protected void executePayment() {
        System.out.println("Processing UPI payment for senderId "+getSenderId());
    }

    @Override
    public void refund() {
        System.out.println("Returning UPI payment "+getTransactionId());
        updateStatus(PaymentStatus.REFUNDED);
    }
}
