package org.product.distributor.dto;

import lombok.Data;
import org.product.distributor.model.Product;
import org.product.distributor.model.ShopkeeperOrder;

import javax.persistence.*;
import java.util.List;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Data
public class OrderProductDTO {

    private Long id;
    private Long productId;
    private String productName;
    private String productShortName;
    private String productBrandName;
    private String productBrandShortName;
    private Double sellingPrice;
    private Double quantity;
    private String unitOfMeasure;
    private Long orderId;
    private Long customSellingPrice;
    private Long productWeightPriceId;
    private Double productWeight;
    private Long customPriceId;

    // case when one product is ordered in diffrent weight so price changes all changes.
    private List<OrderProductDTO> byWeightOrders;

}
