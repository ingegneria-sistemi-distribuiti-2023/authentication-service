package com.isd.authentication.dto;

import com.isd.authentication.commons.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private Date date;
    private Integer userId;
    private Float amount;
    private String circuit;
    private TransactionStatus status;

}
