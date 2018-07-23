package org.product.distributor.repository;

import org.product.distributor.model.Shopkeeper;
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
public interface ShopkeeperRepo extends JpaRepository<Shopkeeper, Long> {

    @Query("SELECT sp FROM Shopkeeper where sp.distributorArea.id = :distributorAreaId and (deleted is null || deleted = false)")
    List<Shopkeeper> findByDistributorArea_Id(@Param("distributorAreaId") Long distributorAreaId);

}
