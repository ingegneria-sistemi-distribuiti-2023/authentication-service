package com.isd.authentication.dto;

/** DTO usato per visualizzare tutte le informazioni relative ad un utente **/
public class UserBalanceTransactionDTO {
    private Integer userId;
    private String username;
    private Integer cashableAmount;
    private Integer bonusAmount;

    public UserBalanceTransactionDTO() {
    }

    public UserBalanceTransactionDTO(Integer userId, String username, Integer cashableAmount, Integer bonusAmount) {
        this.userId = userId;
        this.username = username;
        this.cashableAmount = cashableAmount;
        this.bonusAmount = bonusAmount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCashableAmount() {
        return cashableAmount;
    }

    public void setCashableAmount(Integer cashableAmount) {
        this.cashableAmount = cashableAmount;
    }

    public Integer getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(Integer bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    // TODO: Fai una classe ad hoc (ATTENZIONE: non deve essere uguale a quella dell'entity!) per gestirti tutte le transazioni
}
