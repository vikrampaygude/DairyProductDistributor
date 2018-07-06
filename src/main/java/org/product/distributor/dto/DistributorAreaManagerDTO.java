package org.product.distributor.dto;

import lombok.Data;
import org.product.distributor.model.DistributorArea;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by vikram on 04/07/18.
 *
 */
@Data
public class DistributorAreaManagerDTO {

    private Long id;
    private String name;
    private String distributorAreaName;
    private Long distributorAreaId;

}
