package org.product.distributor.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by vikram on 13/07/18.
 */
@Entity
@Data
public class ShopkeeperCustomPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private ShopkeeperOrder shopkeeperOrder;

    @OneToOne
    private Product product;

    @Column
    private Double price;

    @OneToOne
    private ProductWeightPrice productWeightPrice;

}
