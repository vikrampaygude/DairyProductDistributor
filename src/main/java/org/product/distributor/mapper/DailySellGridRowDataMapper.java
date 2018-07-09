package org.product.distributor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.product.distributor.dto.DailySellGridRowDataDTO;
import org.product.distributor.dto.DistributorDTO;
import org.product.distributor.dto.OrderProductDTO;
import org.product.distributor.dto.ShopkeeperOrderDTO;
import org.product.distributor.model.Distributor;
import org.product.distributor.model.OrderProduct;
import org.product.distributor.model.ShopkeeperOrder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by vikram on 07/07/18.
 */
@Mapper
public interface DailySellGridRowDataMapper {

    DailySellGridRowDataMapper INSTANCE = Mappers.getMapper(DailySellGridRowDataMapper.class);


    @Mappings({
            @Mapping(source = "date", target = "date"),
            @Mapping(source = "shopkeeperOrderMap", target = "shopkeeperOrderDTOListMap" )
    })

    DailySellGridRowDataDTO  getGridDataDTo(
            LocalDate date,
            Map<ShopkeeperOrder, List<OrderProduct>> shopkeeperOrderMap
    );


    Map<ShopkeeperOrderDTO,List<OrderProductDTO>> map(Map<ShopkeeperOrder, List<OrderProduct>> value);
    List<org.product.distributor.dto.OrderProductDTO> map(List<OrderProduct> value);


}
