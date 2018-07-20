package org.product.distributor.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by vikram on 17/07/18.
 *
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
    private Double grandTotal = 0.0;

    @Column
    private Double grandPaid = 0.0;

    @Column
    private Double grandDue = 0.0;

    @Column
    private LocalDate date;

}
