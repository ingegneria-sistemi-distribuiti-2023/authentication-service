package com.isd.authentication.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "balances")
public class Balance {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Float cashable;

    @Column
    private Float bonus;

    @Column(name = "user_id")
    private Integer userId;
    
    /**
     * Mandatory to handle tests
     * */
    @Override
    public boolean equals(Object obj) {
        boolean same = false;

        if (obj != null && obj instanceof Balance){
            same = this.id == ((Balance) obj).getId() &&
                    this.cashable == ((Balance) obj).getCashable() &&
                    this.bonus == ((Balance) obj).getBonus() &&
                    this.userId == ((Balance) obj).getUserId();
        }

        return same;
    }

}
