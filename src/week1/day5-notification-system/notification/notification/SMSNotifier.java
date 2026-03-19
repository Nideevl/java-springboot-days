package notification.notification;

import notification.contract.Sendable;
import notification.model.Transaction;

public class SMSNotifier implements Sendable {
    @Override
    public void send(Transaction t) {
        System.out.println("SMS sent for transaction: " + t.getTransactionId());
    }
}
