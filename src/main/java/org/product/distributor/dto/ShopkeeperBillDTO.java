package org.product.distributor.dto;

import lombok.Data;
import org.product.distributor.model.Shopkeeper;
import org.product.distributor.services.UtilService;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by vikram on 17/07/18.
 */
@Data
public class ShopkeeperBillDTO {
    private Long id;
    private Long shopkeeperId;
    private String shopkeeperName;
    private Double grandTotal;
    private Double grandPaid;
    private Double grandDue;
    private LocalDate date;

    public Double getGrandTotal() {
        grandTotal = UtilService.roundPrice(grandTotal);
        return grandTotal;
    }

    public Double getGrandPaid() {
        grandPaid = UtilService.roundPrice(grandPaid);
        return grandPaid;
    }

    public Double getGrandDue() {
        grandDue = UtilService.roundPrice(grandDue);
        return grandDue;
    }
}
