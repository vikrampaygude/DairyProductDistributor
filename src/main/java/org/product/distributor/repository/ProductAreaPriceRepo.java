package org.product.distributor.repository;

import org.product.distributor.model.ProductAreaPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by vikram on 08/08/18.
 */
@Repository
public interface ProductAreaPriceRepo extends JpaRepository<ProductAreaPrice, Long>{

    @Query("Select pr.price from ProductAreaPrice pr where pr.product.id = :productId and pr.distributorArea.id = :areaId")
    Double getPriceByProductAndArea(@Param("productId") Long productId, @Param("areaId") Long areaId);

    @Query("Select pr from ProductAreaPrice pr where pr.product.id = :productId and pr.distributorArea.id = :areaId")
    ProductAreaPrice findByProductAndArea(@Param("productId") Long productId, @Param("areaId") Long areaId);

}
