package org.product.distributor.services;

import org.product.distributor.dto.ProductDTO;
import org.product.distributor.mapper.ProductMapper;
import org.product.distributor.model.Product;
import org.product.distributor.model.ProductAreaPrice;
import org.product.distributor.repository.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        productRepo.findAll().forEach(d -> productDTOList.add(productMapper.getProductDTO(d,d.getProductWeightPriceList(), d.getDistributorAreaList(), d.getProductAreaPrices())));
        return productDTOList;
    }

    public List<ProductDTO> getAll(Long distributorAreaId){
        List<ProductDTO> productDTOList = new ArrayList<>();
        productRepo.findByDistributorAreaList_Id(distributorAreaId).forEach(d -> productDTOList.add(productMapper.getProductDTO(d,d.getProductWeightPriceList(), d.getDistributorAreaList(), d.getProductAreaPrices())));
        return productDTOList;
    }


    public ProductDTO getById(Long id){
        Product product = productRepo.findById(id).get();
        return productMapper.getProductDTO(product, product.getProductWeightPriceList(), product.getDistributorAreaList(), product.getProductAreaPrices());
    }

    @Transactional
    public Product saveProduct(ProductDTO productDTO){
        Product product = productMapper.getProduct(productDTO, productDTO.getProductWeightPriceDTOList(), productDTO.getDistributorAreaDTOList());
        return productRepo.save(product);
    }

    @Transactional()
    public Product updateProduct(ProductDTO productDTO){
        if(productDTO.getId() == null || productDTO.getId() ==0)
            throw new IllegalArgumentException();

        return productRepo.save(productMapper.getProduct(productDTO, productDTO.getProductWeightPriceDTOList(), productDTO.getDistributorAreaDTOList()));
    }

    public void deteletById(Long id) {
        Optional<Product> productOptional = productRepo.findById(id);
        productOptional.ifPresent(product -> {
            product.setDeleted(true);
            productRepo.save(product);
        });
    }

    public Double getProductPrice(Long productId, Long distributorAreaId){
        return getProductPrice(productRepo.findById(productId).get(), distributorAreaId);
    }

    public Double getProductPrice(Product product, Long distributorAreaId){
        Double price = product.getSellingPrice() ;

        if(product.getProductAreaPrices() !=null){
            Optional<ProductAreaPrice> first = product.getProductAreaPrices()
                    .stream()
                    .filter(productAreaPrice -> productAreaPrice.getDistributorArea() != null && productAreaPrice.getDistributorArea().getId() == distributorAreaId)
                    .findFirst();

            price =  first.isPresent()? first.get().getPrice() : price;
        }
        return price;
    }
}
