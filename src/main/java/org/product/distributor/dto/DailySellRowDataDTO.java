package org.product.distributor.dto;

import lombok.Data;

import java.util.List;

@Data
    public class DailySellRowDataDTO {
    ShopkeeperOrderDTO shopkeeperOrderDTO;
    List<OrderProductDTO> orderProductDTOS;
}
