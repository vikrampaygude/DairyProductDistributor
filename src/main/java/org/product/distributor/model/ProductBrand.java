package org.product.distributor.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikram on 04/07/18.
 */
@Entity
@Data
public class ProductBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(max = 100)
    private String name;

    @Column
    @Size(max = 20)
    private String shortName;

    @OneToMany(
            mappedBy = "productBrand",
            cascade = CascadeType.ALL
    )
    List<Product> productList = new ArrayList<>();

}
