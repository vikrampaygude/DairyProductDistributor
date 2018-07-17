package org.product.distributor.services;

import org.product.distributor.dto.ShopkeeperOrderDTO;
import org.product.distributor.mapper.ShopkeeperOrderMapper;
import org.product.distributor.repository.ShopkeeperOrderRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by vikram on 16/07/18.
 */
@Service
public class DuesService {


    private ShopkeeperOrderRepo shopkeeperOrderRepo;
    private ShopkeeperOrderMapper shopkeeperOrderMapper;

    public DuesService(ShopkeeperOrderRepo shopkeeperOrderRepo, ShopkeeperOrderMapper shopkeeperOrderMapper) {
        this.shopkeeperOrderRepo = shopkeeperOrderRepo;
        this.shopkeeperOrderMapper = shopkeeperOrderMapper;
    }

    public List<ShopkeeperOrderDTO> getDuesByShopkeeperId(Long shopkeeperId){
        return shopkeeperOrderMapper.map(shopkeeperOrderRepo.findAllDues(LocalDate.now().minusMonths(2), shopkeeperId));
    }
}
