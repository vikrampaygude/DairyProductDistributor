package org.product.distributor.dto.order;

import lombok.Data;
import org.product.distributor.dto.OrderProductDTO;
import org.product.distributor.dto.ShopkeeperOrderDTO;
import org.product.distributor.model.OrderProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Data
    public class DailySellRowDataDTO {
    private ShopkeeperOrderDTO shopkeeperOrderDTO;
    private List<OrderProductDTO> orderProductDTOS;
    private Double billTotalPrice;
    private Double billPaidPrice;
    private Double billDuePrice;

    public void processByWeight() {
        for (int i = 0; i < orderProductDTOS.size(); i++) {
            OrderProductDTO orderProductDTO = orderProductDTOS.get(i);
            if(orderProductDTO.getProductWeightPriceId() !=null)
                continue;
            for (int j = 0; j < orderProductDTOS.size(); j++) {
                if ( orderProductDTO!= orderProductDTOS.get(j) && orderProductDTO.getProductId().equals(orderProductDTOS.get(j).getProductId())) {
                    if (orderProductDTO.getByWeightOrders() == null)
                        orderProductDTO.setByWeightOrders(new ArrayList<>());
                    orderProductDTO.getByWeightOrders().add(orderProductDTOS.get(j));
                    orderProductDTOS.remove(j);
                    j--;
                }
            }
        }
    }

    public List<OrderProductDTO> getAllByProductId(Long productId) {
        Predicate<OrderProductDTO> orderProductDTOPredicate = orderProductDTO -> orderProductDTO.getProductId().equals(productId);
        return orderProductDTOS.stream().filter(orderProductDTOPredicate).collect(Collectors.toList());
    }

}