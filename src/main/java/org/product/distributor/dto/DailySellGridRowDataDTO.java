package org.product.distributor.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by vikram on 07/07/18.
 */
@Data
public class DailySellGridRowDataDTO {

    private LocalDate date;
    private DistributorDTO distributorDTO;
    private Map<ShopkeeperOrderDTO, List<OrderProductDTO>> shopkeeperOrderDTOListMap;

}
