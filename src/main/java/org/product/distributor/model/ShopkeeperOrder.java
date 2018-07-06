package org.product.distributor.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Entity
@Data
public class ShopkeeperOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate date;

    @Column
    private String status;

    @Column
    private Double totalAmount;

    @Column
    private Double paidAmount;

    @Column
    private Double dueAmount;

    @OneToOne
    private Shopkeeper shopkeeper;

    @OneToMany(
            mappedBy = "shopkeeperOrder",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<OrderProduct> orderProductList;

}
