package org.product.distributor.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by vikram on 05/07/18.
 */
@Entity
@Data
public class ProductWeightPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double sellingPrice;

    @Column
    private Double purchasePrice;

    @Column
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
