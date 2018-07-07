package org.product.distributor.repository;

import org.product.distributor.model.ProductQuantityPrice;
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
public interface ProductQuantityPriceRepo extends JpaRepository<ProductQuantityPrice, Long> {

    @Query("Select pqr from ProductQuantityPrice pqr where pqr.product.id = :productId")
    List<ProductQuantityPrice> getAllByProducId(@Param("productId") Long productId);
}
