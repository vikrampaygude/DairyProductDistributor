package org.product.distributor.dto.search;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by vikram on 09/07/18.
 */
@Data
public class OrderProductSearchDTO {

    private Long distributorAreaId;
    private String date;
    private LocalDate localDate;

    public LocalDate getLocalDate() {
        localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("d/MM/yyyy"));
        return localDate;
    }
}
