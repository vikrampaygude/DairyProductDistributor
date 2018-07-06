package org.product.distributor.repository;

import org.product.distributor.model.OrderProduct;
import org.product.distributor.model.ShopkeeperOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by vikram on 05/07/18.
 */
public interface OrderProductRepo extends JpaRepository<OrderProduct, Long> {
}
