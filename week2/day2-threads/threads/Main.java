package threads;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount(0);

        Runnable depositTask = () -> {
            for (int i = 0; i < 1000; i++) {
                account.deposit(1);
            }
        };

        Runnable withdrawTask = () -> {
            for (int i = 0; i < 1000; i++) {
                account.withdraw(1);
            }
        };

        Thread t1 = new Thread(depositTask);
        Thread t2 = new Thread(withdrawTask);

        t1.start();
        t2.start();

        t1.join(); // when not used synchronised, t1 reads 50, t2 reads 50
        t2.join(); // t1 writes 51 , t2 overwrites it to 49 , update lost.

        System.out.println("Final balance: " + account.getBalance());
    }
}