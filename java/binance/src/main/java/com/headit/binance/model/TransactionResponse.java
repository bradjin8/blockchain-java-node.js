package com.headit.binance.model;

public class TransactionResponse extends TransactionBase {
    private String signedTransaction = "";

    public String getSignedTransaction() {
        return signedTransaction;
    }

    public void setSignedTransaction(String signedTransaction) {
        this.signedTransaction = signedTransaction;
    }
}
