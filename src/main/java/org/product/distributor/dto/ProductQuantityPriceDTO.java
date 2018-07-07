package org.product.distributor.dto;

import lombok.Data;
import org.product.distributor.model.Product;

import javax.persistence.*;

/**
 * Created by vikram on 05/07/18.
 */
@Data
public class ProductQuantityPriceDTO {

    private Long id;
    private Double sellingPrice;
    private Double purchasePrice;
    private Double quantity;
    private Long productId;
    private String productName;

}
