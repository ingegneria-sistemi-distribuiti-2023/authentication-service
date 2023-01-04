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

    /**
     * Mandatory to handle tests
     * */
    @Override
    public boolean equals(Object obj) {
        boolean same = false;

        if (obj != null && obj instanceof Transaction){
            same = this.id == ((Transaction) obj).getId() &&
                    this.userId == ((Transaction) obj).getUserId() &&
                    this.circuit == ((Transaction) obj).getCircuit() &&
                    this.amount == ((Transaction) obj).getAmount() &&
                    this.status == ((Transaction) obj).getStatus() &&
                    this.date == ((Transaction) obj).getDate();
        }

        return same;
    }

}
