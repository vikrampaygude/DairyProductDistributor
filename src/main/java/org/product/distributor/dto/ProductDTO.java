package org.product.distributor.dto;

import lombok.Data;

import java.util.List;

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
    private String unitOfMeasure;

    private List<ProductQuantityPriceDTO> productQuantityPriceDTOList;
    private List<DistributorAreaDTO> distributorAreaDTOList;

}
