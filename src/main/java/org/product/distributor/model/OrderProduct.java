package org.product.distributor.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Entity
@Data
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Product product;

    @Column
    private Double sellingPrice;

    @Column
    private Double quantity;

    @Column
    private String unitOfMeasure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopkeeper_order_id")
    private ShopkeeperOrder shopkeeperOrder;

}
