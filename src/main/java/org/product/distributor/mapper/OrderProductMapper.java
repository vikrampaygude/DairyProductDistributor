package org.product.distributor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.product.distributor.dto.DistributorAreaDTO;
import org.product.distributor.dto.OrderProductDTO;
import org.product.distributor.model.DistributorArea;
import org.product.distributor.model.OrderProduct;

/**
 * Created by vikram on 04/07/18.
 */
@Mapper
public interface OrderProductMapper {

    OrderProductMapper INSTANCE = Mappers.getMapper(OrderProductMapper.class);

    @Mappings({
            @Mapping(source = "product.id", target = "productId"),
            @Mapping(source = "product.name", target = "productName"),
            @Mapping(source = "shopkeeperOrder.id", target = "orderId")

    })
    OrderProductDTO getOrderProductDTO(OrderProduct orderProduct);


    @Mappings({
            @Mapping(target = "product.id", source = "productId"),
            @Mapping(target = "product.name", source = "productName"),
            @Mapping(target = "shopkeeperOrder.id", source = "orderId")

    })
    OrderProduct getOrderProduct(OrderProductDTO orderProductDTO);
}
