package org.product.distributor.error.config;

/**
 * Created by vikram on 19/07/18.
 */
public enum ErrorCode {

    DAY_ORDER_DELETE_ERROR(1000, ""),
    INVALID_AUTHENTICATION(1001, ""),
    INVALID_CREDENTIAL(1002, "Invalid username or password provided.");

    private final int code;
    private final String responsePhrase;

    ErrorCode(int code, String responsePhrase) {
        this.code = code;
        this.responsePhrase = responsePhrase;
    }
}
