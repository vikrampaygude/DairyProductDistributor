package org.product.distributor.dto;

import lombok.Data;
import org.product.distributor.model.Product;
import org.product.distributor.model.ShopkeeperOrder;

import javax.persistence.*;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Data
public class OrderProductDTO {

    private Long id;
    private Long productId;
    private String productName;
    private Double sellingPrice;
    private Double quantity;
    private String unitOfMeasure;
    private Long orderId;

}
