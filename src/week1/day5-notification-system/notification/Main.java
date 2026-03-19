package notification;

import notification.core.BankService;
import notification.exception.InsufficientFundsException;
import notification.model.Transaction;
import notification.notification.EmailNotifier;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Transaction t1 = new Transaction("TXN001", "sender1", "ACC123", 200.0, "SBI");
        Transaction t2 = new Transaction("TXN002", "sender2", "ACC456", 1000.0, "HDFC");


        try {
            BankService b1 = new BankService(new EmailNotifier());
            b1.transfer(t1, 500.0);
        } catch (InsufficientFundsException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }

        try{
            BankService b2 = new BankService(new EmailNotifier());
            b2.transfer(t2, 800.0);
        } catch (InsufficientFundsException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }
}
