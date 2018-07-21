package org.product.distributor.services;

import org.product.distributor.dto.ProductDTO;
import org.product.distributor.mapper.ProductMapper;
import org.product.distributor.model.Product;
import org.product.distributor.repository.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Service
@Transactional
public class ProductService {

    private ProductRepo productRepo;
    private ProductMapper productMapper;

    public ProductService(ProductRepo productRepo, ProductMapper productMapper) {
        this.productRepo = productRepo;
        this.productMapper = productMapper;
    }

    public List<ProductDTO> getAll(){
        List<ProductDTO> productDTOList = new ArrayList<>();
        productRepo.findAll().forEach(d -> productDTOList.add(productMapper.getProductDTO(d,d.getProductWeightPriceList(), d.getDistributorAreaList())));
        return productDTOList;
    }

    public List<ProductDTO> getAll(Long distributorAreaId){
        List<ProductDTO> productDTOList = new ArrayList<>();
        productRepo.findByDistributorAreaList_Id(distributorAreaId).forEach(d -> productDTOList.add(productMapper.getProductDTO(d,d.getProductWeightPriceList(), d.getDistributorAreaList())));
        return productDTOList;
    }


    public ProductDTO getById(Long id){
        return productMapper.getProductDTO(productRepo.findById(id).get(), productRepo.findById(id).get().getProductWeightPriceList(), productRepo.findById(id).get().getDistributorAreaList());
    }

    public Product saveProduct(ProductDTO productDTO){
        return productRepo.save(productMapper.getProduct(productDTO, productDTO.getProductWeightPriceDTOList(),productDTO.getDistributorAreaDTOList()));
    }

    public Product updateProduct(ProductDTO productDTO){
        if(productDTO.getId() == null || productDTO.getId() ==0)
            throw new IllegalArgumentException();

        return productRepo.save(productMapper.getProduct(productDTO, productDTO.getProductWeightPriceDTOList(), productDTO.getDistributorAreaDTOList()));
    }

}
