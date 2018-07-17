package org.product.distributor.dto;

import lombok.Data;

/**
 * Created by vikram on 13/07/18.
 */
@Data
public class ShopkeeperCustomPriceDTO {

    private Long id;
    private Long shopkeeperId;
    private Long productId;
    private Double price;
    private String shopkeeperName;
    private String shopkeeperAreaName;
    private String productName;
    private Long orderId;
    private Long shopkeeperOrderId;

}
