package notification.contract;

import notification.model.Transaction;

public interface Retryable {
    void retry(Transaction t);
}
