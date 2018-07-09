package org.product.distributor.repository;

import org.product.distributor.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by vikram on 05/07/18.
 */
public interface OrderProductRepo extends JpaRepository<OrderProduct, Long> {

    @Query("SELECT op FROM OrderProduct op where op.shopkeeperOrder.id in :shopkeeperOrderList")
    List<OrderProduct> findByShopkeeperOrderIds(@Param("shopkeeperOrderList") List<Long> shopkeeperOrderList);


}
