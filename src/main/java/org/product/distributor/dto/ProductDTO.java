package org.product.distributor.dto;

import lombok.Data;

/**
 * Created by vikram on 04/07/18.
 *
 */
@Data
public class ProductDTO {

    private Long id;
    private String name;
    private Long brandId;
    private String brandName;
    private Double sellingPrice;
    private Double purchasePrice;

}
