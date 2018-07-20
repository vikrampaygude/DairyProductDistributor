package org.product.distributor.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by vikram on 19/07/18.
 */

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class InvalidDailyOrderDeleteException extends Exception {

    public InvalidDailyOrderDeleteException(String message) {
        super(message);
    }
}
