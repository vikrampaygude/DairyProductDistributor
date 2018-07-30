package org.product.distributor.repository;

import org.product.distributor.model.DistributorArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by vikram on 04/07/18.
 *
 */
@Repository
public interface DistributorAreaRepo extends JpaRepository<DistributorArea, Long> {

    List<DistributorArea> findAllByOrderByIdAsc();
}
