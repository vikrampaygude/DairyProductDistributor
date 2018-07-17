package org.product.distributor.repository;

import org.product.distributor.model.ProductWeightPrice;
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
public interface ProductWeightPriceRepo extends JpaRepository<ProductWeightPrice, Long> {

    @Query("Select pqr from ProductWeightPrice pqr where pqr.product.id = :productId")
    List<ProductWeightPrice> getAllByProducId(@Param("productId") Long productId);
}
