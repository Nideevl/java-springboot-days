package notification.contract;

import notification.model.Transaction;

public interface Sendable {
    void send(Transaction t);
}
