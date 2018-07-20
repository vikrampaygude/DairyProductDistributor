package org.product.distributor.error.config;

import org.product.distributor.error.exception.InvalidDailyOrderCreateReqException;
import org.product.distributor.error.exception.InvalidDailyOrderDeleteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

/**
 * Created by vikram on 19/07/18.
 *
 *
 */
@RestControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{


    @ExceptionHandler(InvalidDailyOrderDeleteException.class)
    public final ResponseEntity<ErrorDetails> handleInvalidDailyOrderDeleteException(InvalidDailyOrderDeleteException e, WebRequest webRequest){

        String details = "Deleting daily order is not allowed at this point as future order present and aid price and total price will be get calculated incorrectly.";
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(LocalDate.now());
        errorDetails.setMessage(e.getMessage());
        errorDetails.setDetails(details);
        errorDetails.setErrorCode(ErrorCode.DAY_ORDER_DELETE_ERROR);
        errorDetails.setActions("Delete all future day orders if they are empty and try to delete this one again." +
                "Make sure to note down total paid and due.");

        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(InvalidDailyOrderCreateReqException.class)
    public final ResponseEntity<ErrorDetails> handleInvalidDailyOrderCreateReqException(InvalidDailyOrderCreateReqException e, WebRequest webRequest){

        String details = "Creating daily order is not allowed at this point. Only tomorrow order is allowed to create.";
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(LocalDate.now());
        errorDetails.setMessage(e.getMessage());
        errorDetails.setDetails(details);
        errorDetails.setErrorCode(ErrorCode.DAY_ORDER_DELETE_ERROR);
        errorDetails.setActions("Wait until day before this date to create order.");

        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.METHOD_NOT_ALLOWED);
    }

}
