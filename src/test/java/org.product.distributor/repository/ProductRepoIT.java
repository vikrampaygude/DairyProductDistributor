package org.product.distributor.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.product.distributor.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by vikram on 08/07/18.
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepoIT {

    @Autowired
    private ProductRepo productRepo;


    @Test
    public void findByDistributorAreaList_Id(){
        //given
        //3 products are added for distributor id 1 in Data.sql
        //when
        List<Product> productList = productRepo.findByDistributorAreaList_Id(1L);
        //then
        Assert.assertEquals(productList.size(),3);
    }

}
