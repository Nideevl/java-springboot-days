package notification.core;

import notification.contract.Sendable;
import notification.exception.BankException;
import notification.exception.InsufficientFundsException;
import notification.model.ProcessStatus;
import notification.model.Transaction;

import java.io.FileWriter;
import java.io.IOException;

public class BankService {
    private Sendable notifier;

    public BankService(Sendable notifier) {
        this.notifier = notifier;
    }

    public void transfer(Transaction t, double balance) throws IOException {
            try (FileWriter audit = new FileWriter("audit.log", true)) {
                if (balance < t.getAmount()) {
                    t.updateStatus(ProcessStatus.FAILED);
                    audit.write("FAILED: " + t.getTransactionId() + "\n");
                    throw new InsufficientFundsException("The amount you are trying to send is out of your father's Audacity...");
                }
                notifier.send(t);
                t.updateStatus(ProcessStatus.COMPLETED);
                audit.write("COMPLETED: " + t.getTransactionId() + "\n");
            }
    }
}
