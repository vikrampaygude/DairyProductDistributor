package org.product.distributor.services;

import org.product.distributor.dto.ShopkeeperDTO;
import org.product.distributor.dto.ShopkeeperOrderDTO;
import org.product.distributor.mapper.ShopkeeperMapper;
import org.product.distributor.mapper.ShopkeeperOrderMapper;
import org.product.distributor.model.OrderProduct;
import org.product.distributor.model.Shopkeeper;
import org.product.distributor.model.ShopkeeperOrder;
import org.product.distributor.repository.OrderProductRepo;
import org.product.distributor.repository.ShopkeeperOrderRepo;
import org.product.distributor.repository.ShopkeeperRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Service
public class ShopkeeperService {

    private ShopkeeperRepo shopkeeperRepo;
    private ShopkeeperMapper shopkeeperMapper;
    private ShopkeeperOrderRepo shopkeeperOrderRepo;

    public ShopkeeperService(ShopkeeperRepo shopkeeperRepo, ShopkeeperMapper shopkeeperMapper, ShopkeeperOrderRepo shopkeeperOrderRepo) {
        this.shopkeeperRepo = shopkeeperRepo;
        this.shopkeeperMapper = shopkeeperMapper;
        this.shopkeeperOrderRepo = shopkeeperOrderRepo;
    }

    public List<ShopkeeperDTO> getAll(){
        List<ShopkeeperDTO> shopkeeperDTOList = new ArrayList<>();
        shopkeeperRepo.findAll().forEach(d -> shopkeeperDTOList.add(shopkeeperMapper.getShopkeeperDTO(d)));
        return shopkeeperDTOList;
    }

    public List<ShopkeeperDTO> getAll(Long distributorAreaId){
        List<ShopkeeperDTO> shopkeeperDTOList = new ArrayList<>();
        shopkeeperRepo.findByDistributorArea_Id(distributorAreaId).forEach(d -> shopkeeperDTOList.add(shopkeeperMapper.getShopkeeperDTO(d)));
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
