package org.product.distributor.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by vikram on 17/07/18.
 */
@Entity
@Data
public class ShopkeeperBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Shopkeeper shopkeeper;

    @Column
    private Double grandTotal;

    @Column
    private Double grandPaid;

    @Column
    private Double grandDue;

}
