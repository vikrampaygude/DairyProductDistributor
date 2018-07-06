package org.product.distributor.repository;

import org.product.distributor.model.ProductQuantityPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by vikram on 04/07/18.
 *
 */
@Repository
public interface ProductQuantityPriceRepo extends JpaRepository<ProductQuantityPrice, Long> {

}
