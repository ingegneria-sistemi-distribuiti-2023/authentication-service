package com.isd.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBalanceTransDTO {
    private Integer userId;
    private String username;
    private Float cashableAmount;
    private Float bonusAmount;
    private Boolean enabled;
    private List<TransactionDTO> transactions;

}
