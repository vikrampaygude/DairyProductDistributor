package org.product.distributor.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.product.distributor.dto.DistributorAreaDTO;
import org.product.distributor.model.Distributor;
import org.product.distributor.model.DistributorArea;

/**
 * Created by vikram on 04/07/18.
 * Tests
 */
public class DistributorAreaMapperTest {


    private DistributorAreaMapper distributorAreaMapper = DistributorAreaMapper.INSTANCE;

    @Test
    public void getDistributorAreaDTOTest(){

        //given
        DistributorArea distributorArea = new DistributorArea();

        distributorArea.setId(101L);
        distributorArea.setName("Katraj Line");

        Distributor distributor = new Distributor();
        distributor.setId(1L);
        distributor.setName("Sai Dairy");

        distributorArea.setDistributor(distributor);

        //when
        DistributorAreaDTO distributorAreaDTO = distributorAreaMapper.getDistributorAreaDTO(distributorArea);

        //then
        Assert.assertEquals(distributorArea.getId(), distributorAreaDTO.getId());
        Assert.assertEquals(distributorArea.getName(), distributorAreaDTO.getName());
        Assert.assertEquals(distributorArea.getDistributor().getId(), distributorAreaDTO.getDistributorId());
        Assert.assertEquals(distributorArea.getDistributor().getName(), distributorAreaDTO.getDistributorName());

    }

    @Test
    public void getDistributorAreaTest(){
        //given
        DistributorAreaDTO distributorAreaDTO = new DistributorAreaDTO();

        distributorAreaDTO.setId(101L);
        distributorAreaDTO.setName("Katraj Line");
        distributorAreaDTO.setDistributorName("Sai Dairy");
        distributorAreaDTO.setDistributorId(1L);

        //when
        DistributorArea distributorArea = distributorAreaMapper.getDistributorArea(distributorAreaDTO);

        //then
        Assert.assertEquals(distributorAreaDTO.getId(), distributorArea.getId());
        Assert.assertEquals(distributorAreaDTO.getName(), distributorArea.getName());
        Assert.assertEquals(distributorAreaDTO.getDistributorId(), distributorArea.getDistributor().getId());
        Assert.assertEquals(distributorAreaDTO.getDistributorName(), distributorArea.getDistributor().getName());

    }


}
