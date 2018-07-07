package org.product.distributor.services;

import org.product.distributor.dto.ProductQuantityPriceDTO;
import org.product.distributor.mapper.ProductQuantityPriceMapper;
import org.product.distributor.model.ProductQuantityPrice;
import org.product.distributor.repository.ProductQuantityPriceRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Service
public class ProductQuantityPriceService {

    private ProductQuantityPriceRepo productQuantityPriceRepo;
    private ProductQuantityPriceMapper productQuantityPriceMapper;

    public ProductQuantityPriceService(ProductQuantityPriceRepo productQuantityPriceRepo, ProductQuantityPriceMapper productQuantityPriceMapper) {
        this.productQuantityPriceRepo = productQuantityPriceRepo;
        this.productQuantityPriceMapper = productQuantityPriceMapper;
    }

    public List<ProductQuantityPriceDTO> getAll(){
        List<ProductQuantityPriceDTO> productQuantityPriceDTOList = new ArrayList<>();
        productQuantityPriceRepo.findAll().forEach(d -> productQuantityPriceDTOList.add(productQuantityPriceMapper.getProductQuantityPriceDTO(d)));
        return productQuantityPriceDTOList;
    }

    public List<ProductQuantityPriceDTO> getAllByProductId(Long productId){
        List<ProductQuantityPriceDTO> productQuantityPriceDTOList = new ArrayList<>();
        productQuantityPriceRepo.getAllByProducId(productId).forEach(d -> productQuantityPriceDTOList.add(productQuantityPriceMapper.getProductQuantityPriceDTO(d)));
        return productQuantityPriceDTOList;
    }



    public ProductQuantityPriceDTO getById(Long id){
        return productQuantityPriceMapper.getProductQuantityPriceDTO(productQuantityPriceRepo.findById(id).get());
    }

    public ProductQuantityPrice saveProductQuantityPrice(ProductQuantityPriceDTO productQuantityPriceDTO){
        return productQuantityPriceRepo.save(productQuantityPriceMapper.getProductQuantityPrice(productQuantityPriceDTO));
    }

    public ProductQuantityPrice updateProductQuantityPrice(ProductQuantityPriceDTO productQuantityPriceDTO){
        if(productQuantityPriceDTO.getId() == null || productQuantityPriceDTO.getId() ==0)
            throw new IllegalArgumentException();

        return productQuantityPriceRepo.save(productQuantityPriceMapper.getProductQuantityPrice(productQuantityPriceDTO));
    }

}
