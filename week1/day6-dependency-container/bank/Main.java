package bank;

import bank.container.DependencyContainer;
import bank.service.BankService;

public class Main {
    public static void main(String[] args) throws Exception {
        DependencyContainer container = new DependencyContainer();
        BankService service = container.getInstance(BankService.class);
        service.processTransaction("TXN001");
    }
}
