package service;

import model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private static TransactionService instance;
    private final List<Transaction> transactionList = new ArrayList<>();

    private TransactionService() {}

    public static TransactionService getInstance() {
        if (instance == null) {
            instance = new TransactionService();
        }
        return instance;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void addTransaction(Transaction t) {
        transactionList.add(t);
    }

}
