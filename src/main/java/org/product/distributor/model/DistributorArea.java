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
public class DistributorArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(max = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "distributor_id")
    private Distributor distributor;

    @ManyToMany(mappedBy = "distributorAreaList")
    private List<Product> productList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DistributorArea that = (DistributorArea) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
