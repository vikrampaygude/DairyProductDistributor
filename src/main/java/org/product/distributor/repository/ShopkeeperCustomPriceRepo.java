package org.product.distributor.repository;

import org.product.distributor.model.ShopkeeperCustomPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by vikram on 13/07/18.
 */
@Repository
public interface ShopkeeperCustomPriceRepo extends JpaRepository<ShopkeeperCustomPrice, Long> {

    @Query("SELECT scp FROM  ShopkeeperCustomPrice scp where scp.product.id = :productId and scp.shopkeeperOrder.id =:shopkeeperOrderId ")
    Optional<ShopkeeperCustomPrice> findByShopkeeperOrderAndProduct(@Param("productId") Long productId, @Param("shopkeeperOrderId") Long shopkeeperOrderId);

    @Query("SELECT scp FROM  ShopkeeperCustomPrice scp where scp.product.id = :productId and scp.shopkeeperOrder.id =:shopkeeperOrderId and scp.productWeightPrice.id =:productWeightPriceId ")
    Optional<ShopkeeperCustomPrice> findByShopkeeperOrderAndProductWeight(@Param("productId") Long productId, @Param("shopkeeperOrderId") Long shopkeeperOrderId,@Param("productWeightPriceId") Long productWeightPriceId);

    List<ShopkeeperCustomPrice> findByShopkeeperOrder_id(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM ShopkeeperCustomPrice scp where  scp.shopkeeperOrder.id =:shopkeeperOrderId ")
    void deleteByShopkeeperOrderId(@Param("shopkeeperOrderId") Long shopkeeperOrderId);

}
