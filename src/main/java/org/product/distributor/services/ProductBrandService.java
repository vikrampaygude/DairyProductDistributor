package org.product.distributor.services;

import org.product.distributor.dto.ProductBrandDTO;
import org.product.distributor.mapper.ProductBrandMapper;
import org.product.distributor.model.ProductBrand;
import org.product.distributor.repository.ProductBrandRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Service
public class ProductBrandService {


    private ProductBrandRepo productBrandRepo;
    private ProductBrandMapper productBrandMapper;

    public ProductBrandService(ProductBrandRepo productBrandRepo, ProductBrandMapper productBrandMapper) {
        this.productBrandRepo = productBrandRepo;
        this.productBrandMapper = productBrandMapper;
    }

    public List<ProductBrandDTO> getAll(){
        List<ProductBrandDTO> productBrandDTOList = new ArrayList<>();
        productBrandRepo.findAll().forEach(d -> productBrandDTOList.add(productBrandMapper.getProductBrandDTO(d)));
        return productBrandDTOList;
    }

    public ProductBrandDTO getById(Long id){
        return productBrandMapper.getProductBrandDTO(productBrandRepo.findById(id).get());
    }

    public ProductBrand saveProductBrand(ProductBrandDTO productBrandDTO){
        return productBrandRepo.save(productBrandMapper.getProductBrand(productBrandDTO));
    }

    public ProductBrand updateProductBrand(ProductBrandDTO productBrandDTO){
        if(productBrandDTO.getId() == null || productBrandDTO.getId() ==0)
            throw new IllegalArgumentException();

        return productBrandRepo.save(productBrandMapper.getProductBrand(productBrandDTO));
    }
}
