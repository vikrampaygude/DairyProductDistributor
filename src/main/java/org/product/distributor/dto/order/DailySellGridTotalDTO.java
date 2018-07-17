package org.product.distributor.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikram on 13/07/18.
 */
@Data
public class DailySellGridTotalDTO {

    private String productName;
    private Long productId;
    private List<TotalByWeight> totalByWeight = new ArrayList<>();



    public void addTotalByWeight(Double weight, Double totalItems){
        boolean found = false;
        for (TotalByWeight tw : totalByWeight) {
            if (tw.getWeight().equals(weight)) {
                tw.setTotalItems(tw.getTotalItems() + totalItems);
                found = true;
                break;
            }
        }
        if(!found)
            totalByWeight.add(new TotalByWeight(weight, totalItems));
    }
}

@Data
@AllArgsConstructor
class TotalByWeight{

    Double weight;
    Double totalItems;

}
