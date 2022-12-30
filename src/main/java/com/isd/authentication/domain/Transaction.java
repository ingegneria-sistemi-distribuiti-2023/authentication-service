package com.isd.authentication.domain;

import com.isd.authentication.commons.TransactionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


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
    private Float amount;

    @Column(nullable = false, length = 45)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column()
    private Date date;

}
