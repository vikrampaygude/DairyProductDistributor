package org.product.distributor.error.config;

import lombok.Data;

import java.time.LocalDate;

/**
 * Created by vikram on 19/07/18.
 */
@Data
public class ErrorDetails {

    private LocalDate timestamp;
    private String message;
    private String details;
    private String actions;
    private ErrorCode errorCode;


}
