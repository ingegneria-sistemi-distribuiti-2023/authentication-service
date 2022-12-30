package com.isd.authentication.dto;

import com.isd.authentication.commons.TransactionStatus;
import com.isd.authentication.domain.Transaction;

import java.util.Date;

public class TransactionDTO {

    private Date date;
    private Integer userId;
    private Float amount;
    private String circuit;
    private TransactionStatus status;

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

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
