package org.product.distributor.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by vikram on 04/07/18.
 *
 */

@Data
public class DistributorDTO {

    private Long id;
    private String name;

    private Set<DistributorAreaDTO> lines = new HashSet<>();

}
