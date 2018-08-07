package org.product.distributor.services;

import org.springframework.stereotype.Component;

/**
 * Created by vikram on 05/08/18.
 */
@Component
public class UtilService {


    /***
     * This is wrapper to Math.round as in future if requirement is change we simply make changes here.
     * @param number
     * @return
     */
    public static double roundPrice(double number){
        return Math.round(number);
    }
}
