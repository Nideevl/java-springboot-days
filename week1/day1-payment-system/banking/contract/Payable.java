package banking.contract;

public interface Payable {
    void process();
    void refund();
    double getAmount();
}

