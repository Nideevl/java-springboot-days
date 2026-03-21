    package bank.repository;

    public class TransactionRepository {
        public void save(String transactionId) {
            System.out.println("[Repository] Saving transaction : "+transactionId);
        }
    }
