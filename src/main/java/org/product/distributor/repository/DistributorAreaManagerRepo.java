package org.product.distributor.repository;

import org.product.distributor.model.DistributorAreaManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by vikram on 04/07/18.
 *
 */
@Repository
public interface DistributorAreaManagerRepo extends JpaRepository<DistributorAreaManager, Long> {

}
