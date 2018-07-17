package org.product.distributor.services;

import org.product.distributor.dto.ShopkeeperCustomPriceDTO;
import org.product.distributor.mapper.ShopkeeperCustomPriceMapper;
import org.product.distributor.model.Product;
import org.product.distributor.model.ShopkeeperCustomPrice;
import org.product.distributor.repository.ShopkeeperCustomPriceRepo;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.Optional;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Service
public class ShopkeeperCustomPriceService {

    private ShopkeeperCustomPriceRepo shopkeeperCustomPriceRepo;
    private ShopkeeperCustomPriceMapper shopkeeperCustomPriceMapper;
    private OrderProductService orderProductService;

    public ShopkeeperCustomPriceService(ShopkeeperCustomPriceRepo shopkeeperCustomPriceRepo, ShopkeeperCustomPriceMapper shopkeeperCustomPriceMapper, OrderProductService orderProductService) {
        this.shopkeeperCustomPriceRepo = shopkeeperCustomPriceRepo;
        this.shopkeeperCustomPriceMapper = shopkeeperCustomPriceMapper;
        this.orderProductService = orderProductService;
    }

    public List<ShopkeeperCustomPriceDTO> getAll(){
        return  shopkeeperCustomPriceMapper.map(shopkeeperCustomPriceRepo.findAll());
    }

    public ShopkeeperCustomPrice save(ShopkeeperCustomPriceDTO shopkeeperCustomPriceDTO) throws OperationNotSupportedException {

        //validation: only allow to add if not present already
        if(shopkeeperCustomPriceRepo.findByShopkeeperOrderAndProduct( shopkeeperCustomPriceDTO.getProductId(), shopkeeperCustomPriceDTO.getShopkeeperOrderId()).isPresent())
            throw new OperationNotSupportedException("Custom price already present for product weight. Please update instead of creating new one.");


        ShopkeeperCustomPrice shopkeeperCustomPrice = shopkeeperCustomPriceMapper.map(shopkeeperCustomPriceDTO);
        shopkeeperCustomPrice = shopkeeperCustomPriceRepo.save(shopkeeperCustomPrice);

        updateProductOrderCustomPrice(shopkeeperCustomPriceDTO.getOrderId(), shopkeeperCustomPrice, shopkeeperCustomPriceDTO.getPrice());

        return shopkeeperCustomPrice;
    }

    public ShopkeeperCustomPrice update(ShopkeeperCustomPriceDTO shopkeeperCustomPriceDTO){
        if(shopkeeperCustomPriceDTO.getId() == null || shopkeeperCustomPriceDTO.getId() ==0)
            throw new IllegalArgumentException();


        ShopkeeperCustomPrice shopkeeperCustomPrice = shopkeeperCustomPriceMapper.map(shopkeeperCustomPriceDTO);
        shopkeeperCustomPrice = shopkeeperCustomPriceRepo.save(shopkeeperCustomPrice);

        updateProductOrderCustomPrice(shopkeeperCustomPriceDTO.getOrderId(), shopkeeperCustomPrice, shopkeeperCustomPriceDTO.getPrice());

        return shopkeeperCustomPrice;

    }

    private void updateProductOrderCustomPrice(Long orderId, ShopkeeperCustomPrice shopkeeperCustomPrice, Double price){
        if(orderId != null)
            orderProductService.updateSellingPrice(orderId, shopkeeperCustomPrice, price);
    }

    public ShopkeeperCustomPriceDTO getById(Long id) {

        Optional<ShopkeeperCustomPrice> shopkeeperCustomPriceOptional = shopkeeperCustomPriceRepo.findById(id);
        if(shopkeeperCustomPriceOptional.isPresent())
            return shopkeeperCustomPriceMapper.map(shopkeeperCustomPriceOptional.get());
        return null;
    }

    public ShopkeeperCustomPrice getProductCustomPrice(Product product, Long shopkeeperOrderId){
        Optional<ShopkeeperCustomPrice> shopkeeperCustomPrice = shopkeeperCustomPriceRepo.findByShopkeeperOrderAndProduct(product.getId(), shopkeeperOrderId);
        return shopkeeperCustomPrice.isPresent()?shopkeeperCustomPrice.get():null;
    }

}
