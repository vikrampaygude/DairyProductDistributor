package org.product.distributor.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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
    private Double totalAmount = 0.0;

    @Column
    private Double paidAmount = 0.0;

    @Column
    private Double dueAmount = 0.0;

    @OneToOne
    private DistributorArea distributorArea;

    @OneToOne
    private Shopkeeper shopkeeper;

    @OneToMany(
            mappedBy = "shopkeeperOrder",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderProduct> orderProductList;

    @Column
    private Boolean deleted;
}
