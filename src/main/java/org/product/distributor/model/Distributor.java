package org.product.distributor.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by vikram on 04/07/18.
 */


@Data
@Entity
public class Distributor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(max = 100)
    private String name;

    @OneToMany(
            mappedBy = "distributor",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    Set<DistributorArea> distributorAreas = new HashSet<>();
}
