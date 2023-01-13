package com.isd.authentication.converter;

import com.isd.authentication.domain.Balance;
import com.isd.authentication.domain.User;
import com.isd.authentication.dto.UserBalanceDTO;
import com.isd.authentication.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserBalanceConverter {

    @Autowired
    private BalanceRepository br;

    public UserBalanceDTO convertToDTO(User user){
        UserBalanceDTO dto = new UserBalanceDTO();

        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEnabled(user.getEnabled());

        Balance currB = br.findByUserId(user.getId());

        // ho evitato il throw dell'errore qui per evitare di gestire su getAll e fare ritornare un oggetto totalmente null
        if (currB != null){
            dto.setCashableAmount(currB.getCashable());
            dto.setBonusAmount(currB.getBonus());
        }

        return dto;
    }
}
