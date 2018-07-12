package org.product.distributor.repository;

import org.product.distributor.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by vikram on 05/07/18.
 */
public interface OrderProductRepo extends JpaRepository<OrderProduct, Long> {

    @Query("SELECT op FROM OrderProduct op where op.shopkeeperOrder.id = :shopkeeperOrderId")
    List<OrderProduct> findByShopkeeperOrderId(@Param("shopkeeperOrderId") Long shopkeeperOrderId);

    @Query("SELECT op FROM OrderProduct op where op.shopkeeperOrder.id in :shopkeeperOrderList order by shopkeeperOrder.id")
    List<OrderProduct> findByShopkeeperOrderIds(@Param("shopkeeperOrderList") List<Long> shopkeeperOrderList);

    @Transactional
    @Modifying
    @Query("UPDATE OrderProduct op SET op.quantity = :quantity where op.id = :id")
    void updateQuanitity(@Param("id") Long id, @Param("quantity") Double quantity);
}
