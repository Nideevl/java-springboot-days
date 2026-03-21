package notification.contract;
import notification.model.Transaction;

public interface Loggable {
    void log(Transaction t);

}
