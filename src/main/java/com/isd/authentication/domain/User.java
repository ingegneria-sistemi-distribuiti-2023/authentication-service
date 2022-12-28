package com.isd.authentication.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45)
    private String username;

    @Column(length = 45)
    private String password;

//    TODO: non necessario al momento
//    @OneToOne
//    @JoinColumn(name = "user_balance_id", nullable = false)
//    private Balance userBalance;
//
//    @OneToOne
//    @JoinColumn(name = "user_transactions_id", nullable = false)
//    private Transaction userTransactions;

}
