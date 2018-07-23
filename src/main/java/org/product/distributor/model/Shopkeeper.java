package org.product.distributor.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by vikram on 04/07/18.
 */
@Entity
@Data
public class Shopkeeper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(max = 100)
    private String name;

    @Column
    private Boolean deleted;

    @Column
    @Size(max = 256)
    private String address;

    @OneToOne
    private DistributorArea distributorArea;

}
