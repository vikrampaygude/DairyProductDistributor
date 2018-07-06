package org.product.distributor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.product.distributor.dto.DistributorDTO;
import org.product.distributor.model.Distributor;

/**
 * Created by vikram on 04/07/18.
 */
@Mapper
public interface DistributorMapper {

    DistributorMapper INSTANCE = Mappers.getMapper(DistributorMapper.class);

    DistributorDTO getDistributorDTO(Distributor distributor);
    Distributor getDistributor(DistributorDTO distributorDto);
}
