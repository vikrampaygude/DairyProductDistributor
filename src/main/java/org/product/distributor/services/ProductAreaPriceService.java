package org.product.distributor.services;

import org.product.distributor.dto.ProductAreaPriceDTO;
import org.product.distributor.mapper.ProductAreaPriceMapper;
import org.product.distributor.model.ProductAreaPrice;
import org.product.distributor.repository.ProductAreaPriceRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Created by vikram on 08/08/18.
 */
@Service
public class ProductAreaPriceService {

    private ProductAreaPriceRepo productAreaPriceRepo;
    private ProductAreaPriceMapper productAreaPriceMapper;
    private OrderProductService orderProductService;

    public ProductAreaPriceService(ProductAreaPriceRepo productAreaPriceRepo, ProductAreaPriceMapper productAreaPriceMapper, OrderProductService orderProductService) {
        this.productAreaPriceRepo = productAreaPriceRepo;
        this.productAreaPriceMapper = productAreaPriceMapper;
        this.orderProductService = orderProductService;
    }

    public void save(ProductAreaPriceDTO productAreaPriceDTO){

        ProductAreaPrice productAreaPrice =  productAreaPriceRepo.findByProductAndArea(productAreaPriceDTO.getProductId(), productAreaPriceDTO.getAreaId());

        if(productAreaPrice == null)
            productAreaPrice = productAreaPriceMapper.map(productAreaPriceDTO);
        else
            productAreaPrice.setPrice(productAreaPriceDTO.getPrice());


        productAreaPriceRepo.save(productAreaPrice);

        orderProductService.applyLatestPrices(productAreaPrice.getDistributorArea().getId(), productAreaPriceDTO.getLocalDate());

    }
}
