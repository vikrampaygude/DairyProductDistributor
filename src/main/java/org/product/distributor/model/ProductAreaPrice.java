package org.product.distributor.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by vikram on 08/08/18.
 */
@Data
@Entity
public class ProductAreaPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Product product;

    @OneToOne
    private DistributorArea distributorArea;

    @Column
    Double price;

}
