package notification.model;

public class Transaction {
    private String transactionId;
    private String senderId;
    private String receiverAccountNumber;
    private double amount;
    private String bankName;
    private ProcessStatus status;

    public Transaction(String transactionId, String senderId, String receiverAccountNumber, double amount, String bankName) {
        this.amount = amount;
        this.bankName = bankName;
        this.senderId = senderId;
        this.receiverAccountNumber = receiverAccountNumber;
        this.transactionId = transactionId;
        this.status = ProcessStatus.PROCESSING;
    }

    public String getTransactionId() { return transactionId; }

    public String getSenderId() { return senderId; }

    public String getReceiverAccountNumber() { return receiverAccountNumber; }

    public double getAmount() { return amount; }

    public String getBankName() { return bankName; }

    public ProcessStatus getStatus() { return status; }

    public void updateStatus(ProcessStatus newStatus) {
        this.status = newStatus;
    }
}