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
public class TransactionResponseDTO {

    private TransactionStatus status;
    private String message;
    private Date time;

}
