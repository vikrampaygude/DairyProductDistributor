package org.product.distributor.services;

import org.product.distributor.dto.ShopkeeperDTO;
import org.product.distributor.mapper.ShopkeeperMapper;
import org.product.distributor.model.Shopkeeper;
import org.product.distributor.repository.ShopkeeperRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Service
public class ShopkeeperService {

    private ShopkeeperRepo shopkeeperRepo;
    private ShopkeeperMapper shopkeeperMapper;

    public ShopkeeperService(ShopkeeperRepo shopkeeperRepo, ShopkeeperMapper shopkeeperMapper) {
        this.shopkeeperRepo = shopkeeperRepo;
        this.shopkeeperMapper = shopkeeperMapper;
    }

    public List<ShopkeeperDTO> getAll(){
        List<ShopkeeperDTO> shopkeeperDTOList = new ArrayList<>();
        shopkeeperRepo.findAll().forEach(d -> shopkeeperDTOList.add(shopkeeperMapper.getShopkeeperDTO(d)));
        return shopkeeperDTOList;
    }

    public ShopkeeperDTO getById(Long id){
        return shopkeeperMapper.getShopkeeperDTO(shopkeeperRepo.findById(id).get());
    }

    public Shopkeeper saveShopkeeper(ShopkeeperDTO shopkeeperDTO){
        return shopkeeperRepo.save(shopkeeperMapper.getShopkeeper(shopkeeperDTO));
    }

    public Shopkeeper updateShopkeeper(ShopkeeperDTO shopkeeperDTO){
        if(shopkeeperDTO.getId() == null || shopkeeperDTO.getId() ==0)
            throw new IllegalArgumentException();

        return shopkeeperRepo.save(shopkeeperMapper.getShopkeeper(shopkeeperDTO));
    }

}
