package notification.notification;

import notification.contract.Loggable;
import notification.contract.Sendable;
import notification.model.Transaction;

public class EmailNotifier implements Sendable, Loggable {
    @Override
    public void send(Transaction t) {
        System.out.println("Email sent for transaction: " + t.getTransactionId());
    }

    @Override
    public void log(Transaction t) {
        System.out.println("Logged transaction: " + t.getTransactionId() + " status: " + t.getStatus());
    }
}
