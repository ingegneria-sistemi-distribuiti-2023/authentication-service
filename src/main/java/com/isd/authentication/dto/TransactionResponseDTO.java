package com.isd.authentication.dto;

import com.isd.authentication.commons.TransactionStatus;

import java.util.Date;

public class TransactionResponseDTO {

    // FIXME:
    private String status;
    private String message;
    private Date time;

    public TransactionResponseDTO() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
