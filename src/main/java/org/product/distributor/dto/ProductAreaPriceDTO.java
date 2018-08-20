package org.product.distributor.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by vikram on 08/08/18.
 */
@Data
public class ProductAreaPriceDTO {

    private Long productId;
    private Long areaId;
    private Double price;

    private String date;
    private LocalDate localDate;

    public LocalDate getLocalDate() {
        if(date == null)
            return null;
        localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("d/MM/yyyy"));
        return localDate;
    }

}
