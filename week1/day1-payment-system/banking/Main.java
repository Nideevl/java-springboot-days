import banking.contract.Payable;
import banking.payment.CreditCardPayment;
import banking.payment.NetBankingPayment;
import banking.payment.UPIPayment;

import java.util.List;

public class Main {
    public static void main(String []args) {
        List<Payable> payments = List.of(
                new CreditCardPayment("sender1",500.0, "1234-345","Rahul"),
                new NetBankingPayment("sender2", 600.0, "12334234523A", "SBI"),
                new UPIPayment("sender3", 900.0, "raj@upipytes")
        );

        for( Payable payable : payments) {
            payable.process();
            System.out.println("Processing.....");
            System.out.println("--------------------------------");
        }

        double total = payments.stream()
                .filter(p -> p.getAmount() > 500)
                .mapToDouble(Payable::getAmount)
                .sum();
        System.out.println("Total amount for payments above 550: " + total);
    }
}
