package com.isd.authentication.dto;

import java.util.LinkedList;
import java.util.List;

/** DTO usato per visualizzare tutte le informazioni relative ad un utente **/
public class UserBalanceTransDTO {
    private Integer userId;
    private String username;
    private Float cashableAmount;
    private Float bonusAmount;
    private List<TransactionDTO> transactions;

    public UserBalanceTransDTO() {
        this.transactions = new LinkedList<>();
    }

    public UserBalanceTransDTO(Integer userId, String username, Float cashableAmount, Float bonusAmount) {
        this.userId = userId;
        this.username = username;
        this.cashableAmount = cashableAmount;
        this.bonusAmount = bonusAmount;
        this.transactions = new LinkedList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Float getCashableAmount() {
        return cashableAmount;
    }

    public void setCashableAmount(Float cashableAmount) {
        this.cashableAmount = cashableAmount;
    }

    public Float getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(Float bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }
    // TODO: Fai una classe ad hoc (ATTENZIONE: non deve essere uguale a quella dell'entity!) per gestirti tutte le transazioni
}