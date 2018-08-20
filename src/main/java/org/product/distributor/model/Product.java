package org.product.distributor.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by vikram on 04/07/18.
 *
 */
@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(max = 100)
    private String name;

    @Column
    @Size(max = 20)
    private String shortName;

    @Column
    @Size(max = 20)
    private String unitOfMeasure;

    @ManyToOne
    @JoinColumn(name = "product_brand_id")
    private ProductBrand productBrand;

    @Column
    private Double sellingPrice;

    @Column
    private Double purchasePrice;

    @Column
    private Integer uiSequence;

    @ManyToMany(cascade = { CascadeType.MERGE,CascadeType.REFRESH })
    @JoinTable(
            name = "Distributor_Area_Product",
            joinColumns = { @JoinColumn(name = "product_id") },
            inverseJoinColumns = { @JoinColumn(name = "distributor_area_id") }
    )
    List<DistributorArea> distributorAreaList = new ArrayList<>();

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProductWeightPrice> productWeightPriceList;

    @Column
    private Boolean deleted;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "product"
    )
    private List<ProductAreaPrice> productAreaPrices;
}

