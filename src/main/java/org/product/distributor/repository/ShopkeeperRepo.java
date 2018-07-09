package org.product.distributor.repository;

import org.product.distributor.model.Shopkeeper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vikram on 04/07/18.
 *
 */
@Repository
public interface ShopkeeperRepo extends JpaRepository<Shopkeeper, Long> {

    List<Shopkeeper> findByDistributorArea_Id(Long id);

}
