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
    private Integer cashable;

    @Column
    private Integer bonus;

    @Column(name = "user_id")
    private Integer userId;

    // TODO: non necessaria al momento ....
//    @OneToOne(mappedBy = "userBalance", fetch = FetchType.LAZY)
//    private User userBalance;

}
