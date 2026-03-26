package threads;

public class BankAccount {
    private int balance;

    public BankAccount(int initialBalance) { this.balance = initialBalance; }

    public synchronized void deposit(int amount) { this.balance += amount; }

    public synchronized void withdraw(int amount) { this.balance -= amount; }

    public int getBalance() { return balance; }
}