package org.product.distributor.services;

import org.product.distributor.dto.ProductWeightPriceDTO;
import org.product.distributor.mapper.ProductWeightPriceMapper;
import org.product.distributor.model.ProductWeightPrice;
import org.product.distributor.repository.ProductWeightPriceRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Service
public class ProductWeightPriceService {

    private ProductWeightPriceRepo productWeightPriceRepo;
    private ProductWeightPriceMapper productWeightPriceMapper;

    public ProductWeightPriceService(ProductWeightPriceRepo productWeightPriceRepo, ProductWeightPriceMapper productWeightPriceMapper) {
        this.productWeightPriceRepo = productWeightPriceRepo;
        this.productWeightPriceMapper = productWeightPriceMapper;
    }

    public List<ProductWeightPriceDTO> getAll(){
        List<ProductWeightPriceDTO> productWeightPriceDTOList = new ArrayList<>();
        productWeightPriceRepo.findAll().forEach(d -> productWeightPriceDTOList.add(productWeightPriceMapper.getProductWeightPriceDTO(d)));
        return productWeightPriceDTOList;
    }

    public List<ProductWeightPriceDTO> getAllByProductId(Long productId){
        List<ProductWeightPriceDTO> productWeightPriceDTOList = new ArrayList<>();
        productWeightPriceRepo.getAllByProducId(productId).forEach(d -> productWeightPriceDTOList.add(productWeightPriceMapper.getProductWeightPriceDTO(d)));
        return productWeightPriceDTOList;
    }



    public ProductWeightPriceDTO getById(Long id){
        return productWeightPriceMapper.getProductWeightPriceDTO(productWeightPriceRepo.findById(id).get());
    }

    public ProductWeightPrice saveProductWeightPrice(ProductWeightPriceDTO productWeightPriceDTO){
        return productWeightPriceRepo.save(productWeightPriceMapper.getProductWeightPrice(productWeightPriceDTO));
    }

    public ProductWeightPrice updateProductWeightPrice(ProductWeightPriceDTO productWeightPriceDTO){
        if(productWeightPriceDTO.getId() == null || productWeightPriceDTO.getId() ==0)
            throw new IllegalArgumentException();

        return productWeightPriceRepo.save(productWeightPriceMapper.getProductWeightPrice(productWeightPriceDTO));
    }

}
