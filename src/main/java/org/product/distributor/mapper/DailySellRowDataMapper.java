package org.product.distributor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.product.distributor.dto.order.DailySellRowDataDTO;
import org.product.distributor.dto.OrderProductDTO;
import org.product.distributor.dto.ShopkeeperOrderDTO;
import org.product.distributor.model.OrderProduct;
import org.product.distributor.model.ShopkeeperOrder;

import java.util.List;

/**
 * Created by vikram on 11/07/18.
 */
@Mapper
public interface DailySellRowDataMapper {

    DailySellRowDataMapper INSTANCE = Mappers.getMapper(DailySellRowDataMapper.class);

    @Mappings({
            @Mapping(source = "shopkeeperOrder", target = "shopkeeperOrderDTO"),
            @Mapping(source = "orderProducts", target = "orderProductDTOS")
    })
    DailySellRowDataDTO getDailySellRowDataDTO(ShopkeeperOrder shopkeeperOrder, List<OrderProduct> orderProducts);

    List<OrderProductDTO> map(List<OrderProduct> value);

    @Mappings({
            @Mapping(source = "shopkeeper.id", target = "shopkeeperId"),
            @Mapping(source = "shopkeeper.name", target = "shopkeeperName"),
            @Mapping(source = "shopkeeper.rowSeparator", target = "rowSeparator")

    })
    ShopkeeperOrderDTO map(ShopkeeperOrder shopkeeperOrder);


    @Mappings({
            @Mapping(source = "product.id", target = "productId"),
            @Mapping(source = "product.shortName", target = "productShortName"),
            @Mapping(source = "product.unitOfMeasure", target = "unitOfMeasure"),
            @Mapping(source = "product.name", target = "productName"),
            @Mapping(source = "product.productBrand.name", target = "productBrandName"),
            @Mapping(source = "product.productBrand.shortName", target = "productBrandShortName"),
            @Mapping(source = "shopkeeperOrder.id", target = "orderId"),
            @Mapping(source = "productWeightPrice.id", target = "productWeightPriceId"),
            @Mapping(source = "productWeightPrice.weight", target = "productWeight"),
            @Mapping(source = "shopkeeperCustomPrice.id", target = "customPriceId")
    })
    OrderProductDTO map(OrderProduct orderProduct);

}
