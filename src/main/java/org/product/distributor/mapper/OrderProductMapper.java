package org.product.distributor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.product.distributor.dto.OrderProductDTO;
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
            @Mapping(source = "product.shortName", target = "productShortName"),
            @Mapping(source = "product.unitOfMeasure", target = "unitOfMeasure"),
            @Mapping(source = "product.productBrand.name", target = "productBrandName"),
            @Mapping(source = "product.productBrand.shortName", target = "productBrandShortName"),
            @Mapping(source = "shopkeeperOrder.id", target = "orderId"),
            @Mapping(source = "productWeightPrice.id", target = "productWeightPriceId"),
            @Mapping(source = "productWeightPrice.weight", target = "productWeight"),
            @Mapping(source = "shopkeeperCustomPrice.id", target = "customPriceId")
    })
    OrderProductDTO getOrderProductDTO(OrderProduct orderProduct);


    @Mappings({
            @Mapping(target = "product.id", source = "productId"),
            @Mapping(target = "product.name", source = "productName"),
            @Mapping(target = "product.shortName", source = "productShortName"),
            @Mapping(target = "product.unitOfMeasure", source = "unitOfMeasure"),
            @Mapping(target = "product.productBrand.name", source = "productBrandName"),
            @Mapping(target = "product.productBrand.shortName", source = "productBrandShortName"),
            @Mapping(target = "shopkeeperOrder.id", source = "orderId"),
            @Mapping(target = "productWeightPrice.id", source = "productWeightPriceId"),
            @Mapping(target = "productWeightPrice.weight", source = "productWeight"),
            @Mapping(target = "shopkeeperCustomPrice.id", source = "customPriceId")
    })
    OrderProduct getOrderProduct(OrderProductDTO orderProductDTO);

    @Mappings({
            @Mapping(target = "product.id", source = "productId"),
            @Mapping(target = "product.name", source = "productName"),
            @Mapping(target = "product.shortName", source = "productShortName"),
            @Mapping(target = "product.unitOfMeasure", source = "unitOfMeasure"),
            @Mapping(target = "product.productBrand.name", source = "productBrandName"),
            @Mapping(target = "product.productBrand.shortName", source = "productBrandShortName"),
            @Mapping(target = "shopkeeperOrder.id", source = "orderId"),
            @Mapping(target = "productWeightPrice.id", source = "productWeightPriceId"),
            @Mapping(target = "productWeightPrice.weight", source = "productWeight")
    })
    OrderProduct mapWithoutCustomPrice(OrderProductDTO orderProductDTO);



}
