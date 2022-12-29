package com.isd.authentication.dto;

import com.isd.authentication.commons.TransactionStatus;

import java.util.Date;

public class TransactionDTO {

    private Date date;
    private Integer userId;
    private Float amount;
    private String circuit;
    // FIXME:
    private String status;

    public TransactionDTO() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getCircuit() {
        return circuit;
    }

    public void setCircuit(String circuit) {
        this.circuit = circuit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
