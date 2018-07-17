package org.product.distributor.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by vikram on 16/07/18.
 */
@Data
public class DuesByShopkeeperDTO {

    private String shopkeeperName;
    private String shopkeeperAreaName;
    private List<ShopkeeperOrderDTO> orderDTOS;
}

