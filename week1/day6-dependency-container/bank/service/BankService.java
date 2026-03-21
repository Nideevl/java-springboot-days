package bank.service;

import bank.annotation.Inject;
import bank.repository.TransactionRepository;

public class BankService {

    @Inject
    TransactionRepository transactionRepository;

    public void processTransaction(String transactionId){
        transactionRepository.save(transactionId);
    }
}
