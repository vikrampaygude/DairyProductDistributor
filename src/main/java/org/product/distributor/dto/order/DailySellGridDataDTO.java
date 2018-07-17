package org.product.distributor.dto.order;

import lombok.Data;
import org.product.distributor.dto.OrderProductDTO;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by vikram on 07/07/18.
 */
@Data
public class DailySellGridDataDTO {

    private LocalDate date;
    private List<DailySellRowDataDTO> dailySellRowDataDTOList;
    private List<DailySellGridTotalDTO> dailySellGridTotalDTOS = new ArrayList<>();
    private Double grandTotalAmount;
    private Double grandTotalPaidAmount;
    private Double grandTotalDueAmount;

    public void calculateTotalRow(){
        Map<Long, List<OrderProductDTO>> prodIdOrderProdMap = getProductOrderProductMap();
        for (Map.Entry<Long, List<OrderProductDTO>> entry : prodIdOrderProdMap.entrySet())
        {
            for (OrderProductDTO orderProductDTO : entry.getValue()) {
                if(orderProductDTO.getByWeightOrders() == null){
                    addtByProductId(orderProductDTO.getProductId(), 1.0, orderProductDTO.getQuantity());// by default 1 kg,liter etc
                }else{
                    addtByProductId(orderProductDTO.getProductId(), 1.0, orderProductDTO.getQuantity());// by default 1 kg,liter etc
                    for (OrderProductDTO byWeightOrder : orderProductDTO.getByWeightOrders()) {
                        addtByProductId(orderProductDTO.getProductId(), byWeightOrder.getProductWeight(), byWeightOrder.getQuantity());
                    }
                }
            }
        }
    }

    public void  addtByProductId(Long productId, Double weight, Double quantity){
        Optional<DailySellGridTotalDTO> dailySellGridTotalDTO = dailySellGridTotalDTOS.stream().filter(totalDTO -> totalDTO.getProductId().equals(productId)).findFirst();
        if(dailySellGridTotalDTO.isPresent())
             dailySellGridTotalDTO.get().addTotalByWeight(weight, quantity);
        else
            addNewTotalByProductId(productId, "", weight, quantity);
    }

    public void addNewTotalByProductId(Long productId, String productName, Double weight, Double quantity){
        DailySellGridTotalDTO dailySellGridTotalDTO = new DailySellGridTotalDTO();
        dailySellGridTotalDTO.setProductId(productId);
        dailySellGridTotalDTO.setProductName(productName);
        dailySellGridTotalDTO.addTotalByWeight(weight, quantity);

        dailySellGridTotalDTOS.add(dailySellGridTotalDTO);

    }

    public Map<Long, List<OrderProductDTO>> getProductOrderProductMap(){

        Map<Long, List<OrderProductDTO>> productOrderProductDTOMap = new HashMap<>();

        for (DailySellRowDataDTO dailySellRowDataDTO : dailySellRowDataDTOList) {
            for (OrderProductDTO orderProductDTO : dailySellRowDataDTO.getOrderProductDTOS()) {
                if(productOrderProductDTOMap.containsKey(orderProductDTO.getProductId())){
                    List<OrderProductDTO> orderProductDTOS = productOrderProductDTOMap.get(orderProductDTO.getProductId());
                    orderProductDTOS.add(orderProductDTO);
                }else{
                    List<OrderProductDTO> orderProductDTOS = new ArrayList<>();
                    orderProductDTOS.add(orderProductDTO);
                    productOrderProductDTOMap.put(orderProductDTO.getProductId(), orderProductDTOS);
                }
            }
        }

        return productOrderProductDTOMap;
    }
}

