package org.product.distributor.repository;

import org.product.distributor.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vikram on 04/07/18.
 *
 */
@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p inner join p.distributorAreaList da where  da.id = :distributorAreaId and (p.deleted is null  or p.deleted = false  ) order by p.id")
    List<Product> findByDistributorAreaList_Id(@Param("distributorAreaId") Long distributorAreaId);

    @Query("SELECT p FROM Product p where p.deleted is null  or p.deleted = false  ")
    List<Product> findAll();
}
