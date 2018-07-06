package org.product.distributor.services;

import org.product.distributor.dto.ShopkeeperOrderDTO;
import org.product.distributor.mapper.ShopkeeperOrderMapper;
import org.product.distributor.model.ShopkeeperOrder;
import org.product.distributor.repository.ShopkeeperOrderRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Service
public class ShopkeeperOrderService {


    private ShopkeeperOrderRepo shopkeeperOrderRepo;
    private ShopkeeperOrderMapper shopkeeperOrderMapper;

    public ShopkeeperOrderService(ShopkeeperOrderRepo shopkeeperOrderRepo, ShopkeeperOrderMapper shopkeeperOrderMapper) {
        this.shopkeeperOrderRepo = shopkeeperOrderRepo;
        this.shopkeeperOrderMapper = shopkeeperOrderMapper;
    }

    public List<ShopkeeperOrderDTO> getAll(){
        List<ShopkeeperOrderDTO> shopkeeperOrderDTOList = new ArrayList<>();
        shopkeeperOrderRepo.findAll().forEach(d -> shopkeeperOrderDTOList.add(shopkeeperOrderMapper.getShopkeeperOrderDTO(d)));
        return shopkeeperOrderDTOList;
    }

    public ShopkeeperOrderDTO getById(Long id){
        return shopkeeperOrderMapper.getShopkeeperOrderDTO(shopkeeperOrderRepo.findById(id).get());
    }

    public ShopkeeperOrder saveShopkeeperOrder(ShopkeeperOrderDTO shopkeeperOrderDTO){
        return shopkeeperOrderRepo.save(shopkeeperOrderMapper.getShopkeeperOrder(shopkeeperOrderDTO));
    }

    public ShopkeeperOrder updateShopkeeperOrder(ShopkeeperOrderDTO shopkeeperOrderDTO){
        if(shopkeeperOrderDTO.getId() == null || shopkeeperOrderDTO.getId() ==0)
            throw new IllegalArgumentException();

        return shopkeeperOrderRepo.save(shopkeeperOrderMapper.getShopkeeperOrder(shopkeeperOrderDTO));
    }

}
