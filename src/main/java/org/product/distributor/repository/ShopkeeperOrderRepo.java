package org.product.distributor.repository;

import org.product.distributor.model.ShopkeeperOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by vikram on 05/07/18.
 */
public interface ShopkeeperOrderRepo extends JpaRepository<ShopkeeperOrder, Long> {

    @Query("SELECT count(id) FROM ShopkeeperOrder so where so.date = :date and so.shopkeeper.id = :shopkeeperId")
    Integer countByShopkeeperIdAndDate(@Param("date") LocalDate date, @Param("shopkeeperId") Long shopkeeperId);

    @Query("SELECT so FROM ShopkeeperOrder so where so.date = :date and so.shopkeeper.id = :shopkeeperId")
    List<ShopkeeperOrder> findByShopkeeperIdAndDate(@Param("date") LocalDate date, @Param("shopkeeperId") Long shopkeeperId);

    @Query("SELECT so FROM ShopkeeperOrder so where so.date = :date and so.distributorArea.id = :distributorId")
    List<ShopkeeperOrder> findActiveByDistributorAreaAndDate(@Param("date") LocalDate date,@Param("distributorId") Long distributorId);


}
