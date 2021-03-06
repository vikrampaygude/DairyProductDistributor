package org.product.distributor.dto;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by vikram on 04/07/18.
 */
@Data
public class ShopkeeperDTO {

    private Long id;
    private String name;
    private String address;
    private String distributorAreaName;
    private Long distributorAreaId;
    private String distributorName;
    private Long distributorId;
    private Integer uiSequence;
    private Boolean rowSeparator;

}
