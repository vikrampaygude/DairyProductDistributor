package org.product.distributor.repository;

import org.product.distributor.model.ShopkeeperOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by vikram on 05/07/18.
 */
public interface ShopkeeperOrderRepo extends JpaRepository<ShopkeeperOrder, Long> {

    @Query("SELECT count(id) FROM ShopkeeperOrder so where so.date = :date and so.shopkeeper.id = :shopkeeperId")
    Integer countByShopkeeperIdAndDate(@Param("date") LocalDate date, @Param("shopkeeperId") Long shopkeeperId);

    @Query("SELECT count(id) FROM ShopkeeperOrder so where so.date = :date and so.distributorArea.id = :distributorAreaId")
    Integer countBydistributorAreaIdAndDate(@Param("date") LocalDate date, @Param("distributorAreaId") Long distributorAreaId);


    @Query("SELECT so FROM ShopkeeperOrder so where so.date = :date and so.shopkeeper.id = :shopkeeperId")
    List<ShopkeeperOrder> findByShopkeeperIdAndDate(@Param("date") LocalDate date, @Param("shopkeeperId") Long shopkeeperId);

    @Query("SELECT so FROM ShopkeeperOrder so where so.date = :date and so.shopkeeper.id = :shopkeeperId")
    Optional<ShopkeeperOrder> findOneByShopkeeperIdAndDate(@Param("date") LocalDate date, @Param("shopkeeperId") Long shopkeeperId);

    @Query("SELECT so FROM ShopkeeperOrder so where so.date = :date and so.distributorArea.id = :distributorId")
    List<ShopkeeperOrder> findActiveByDistributorAreaAndDate(@Param("date") LocalDate date,@Param("distributorId") Long distributorId);

    @Query("SELECT so FROM ShopkeeperOrder so where so.date > :date and so.shopkeeper.id = :shopkeeperId and (so.dueAmount !=null and so.dueAmount > 0)")
    List<ShopkeeperOrder> findAllDues(@Param("date") LocalDate date, @Param("shopkeeperId") Long shopkeeperId);


    @Transactional
    @Modifying
    @Query("UPDATE ShopkeeperOrder so SET paidAmount=:paidAmount where id=:id")
    void updatePaidAmount(@Param("id") Long id, @Param("paidAmount") Double paidAmount);
}
