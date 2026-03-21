package banking.payment;

import banking.core.AbstractPayment;
import banking.model.PaymentStatus;

public class CreditCardPayment extends AbstractPayment {
    private final String cardNumber;
    private final String cardHolderName;

    public CreditCardPayment(String senderId, double amount , String cardNumber, String cardHolderName){
        super(senderId, amount);
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    @Override
    protected void executePayment() {
        System.out.println("Processing credit card payment for "+cardHolderName);
    }

    @Override
    public void refund() {
        System.out.println("Returning credit card payment "+getTransactionId());
        updateStatus(PaymentStatus.REFUNDED);
    }

}
