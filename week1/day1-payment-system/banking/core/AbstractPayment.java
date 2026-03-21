package banking.core;

import banking.contract.Payable;
import banking.model.PaymentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class AbstractPayment implements Payable {
    private String transactionId;
    private String senderId;
    private double amount;
    private PaymentStatus status;
    private LocalDateTime timestamp;

    protected String getTransactionId() { return transactionId; }
    protected String getSenderId() { return senderId; }
    public double getAmount() { return amount; }
    protected PaymentStatus getStatus() { return status; }
    protected LocalDateTime getTimestamp() { return timestamp; }

    protected AbstractPayment(String senderId, double amount) {
        this.transactionId = UUID.randomUUID().toString();
        this.amount = amount;
        this.senderId = senderId;
        this.status = PaymentStatus.PENDING;
        this.timestamp = LocalDateTime.now();
    }

    protected void updateStatus(PaymentStatus newStatus) {
        this.status = newStatus;
    }

    private void validateAmount() {
        if(this.amount <= 0) {
            throw new IllegalArgumentException(
                    "Payment amount must be greater than 0. Got " + this.amount
            );
        }
    }

    private void checkNotAlreadyProcessed() {
        if(this.status == PaymentStatus.COMPLETED) {
            throw new IllegalStateException (
                    "Payment "+ this.transactionId +" is already processed."
            );
        }
    }

    private void logAttempts() {
        System.out.println("LOG Attempt \n"+
                "TransactionId - "+this.transactionId+"\n"+
                "Amount - "+this.amount+"\n"+
                "SenderId - "+this.senderId+"\n"+
                "Status - "+this.status+"\n"+
                "TimeStamp - "+this.timestamp
        );
    }

    public final void process() {
        validateAmount();
        checkNotAlreadyProcessed();
        logAttempts();
        updateStatus(PaymentStatus.PROCESSING);
        executePayment();
        updateStatus(PaymentStatus.COMPLETED);
    }

    protected abstract void executePayment();

    public abstract void refund();
}
