package org.product.distributor.dto;

import lombok.Data;
import org.product.distributor.model.Shopkeeper;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import java.time.LocalDate;

/**
 * Created by vikram on 05/07/18.
 */
@Data
public class ShopkeeperOrderDTO {

    private Long id;
    private LocalDate date;
    private String status;
    private Double totalAmount;
    private Double paidAmount;
    private Double dueAmount;
    private Long shopkeeperId;
    private String shopkeeperName;
    private Boolean rowSeparator;

}
