package org.product.distributor.repository;

import org.product.distributor.model.ShopkeeperBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by vikram on 17/07/18.
 *
 */
@Repository
public interface ShopkeeperBillRepo extends JpaRepository<ShopkeeperBill, Long> {

    @Query("SELECT sb FROM ShopkeeperBill sb WHERE sb.date = :date and sb.shopkeeper.id = :shopkeeperId")
    Optional<ShopkeeperBill> findByDateAndShopkeeper(@Param("date")LocalDate date, @Param("shopkeeperId") Long shopkeeperId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ShopkeeperBill sb WHERE sb.date = :date and sb.shopkeeper.id = :shopkeeperId")
    void delete(@Param("date") LocalDate date,@Param("shopkeeperId") Long shopkeeperId);
}
