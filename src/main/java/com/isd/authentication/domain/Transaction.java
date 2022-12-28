package com.isd.authentication.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(nullable = false, length = 45)
    private String circuit;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false, length = 45)
    private String status; // FIXME: swap to enum

    // TODO: non necessario al momento
//    @OneToOne(
//            mappedBy = "userTransactions",
//            fetch = FetchType.LAZY
//    )
//    private User userTransactions;

}
