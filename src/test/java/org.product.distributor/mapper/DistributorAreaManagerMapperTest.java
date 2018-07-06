package org.product.distributor.mapper;

import static org.junit.Assert.*;

import org.junit.Test;
import org.product.distributor.dto.DistributorAreaManagerDTO;
import org.product.distributor.model.DistributorArea;
import org.product.distributor.model.DistributorAreaManager;

/**
 * Created by vikram on 05/07/18.
 */
public class DistributorAreaManagerMapperTest {

    private DistributorAreaManagerMapper distributorAreaManagerMapper = DistributorAreaManagerMapper.INSTANCE;

    @Test
    public void getDistributorAreaManagerDTO(){
        //given
        DistributorAreaManager distributorAreaManager = new DistributorAreaManager();
        distributorAreaManager.setId(222L);
        distributorAreaManager.setName("Rahul Pawar");

        DistributorArea distributorArea = new DistributorArea();
        distributorArea.setId(121L);
        distributorArea.setName("Katraj Line");

        distributorAreaManager.setArea(distributorArea);
        //when
        DistributorAreaManagerDTO distributorAreaManagerDTO = distributorAreaManagerMapper.getDistributorAreaManagerDTO(distributorAreaManager);
        //then

        assertEquals(distributorAreaManager.getId(),distributorAreaManagerDTO.getId());
        assertEquals(distributorAreaManager.getName(),distributorAreaManagerDTO.getName());
        assertEquals(distributorAreaManager.getArea().getId(),distributorAreaManagerDTO.getDistributorAreaId());
        assertEquals(distributorAreaManager.getArea().getName(),distributorAreaManagerDTO.getDistributorAreaName());

    }

    @Test
    public void getDistributorAreaManager(){
        //given
        DistributorAreaManagerDTO distributorAreaManagerDTO = new DistributorAreaManagerDTO();
        distributorAreaManagerDTO.setId(222L);
        distributorAreaManagerDTO.setName("Rahul Pawar");
        distributorAreaManagerDTO.setDistributorAreaId(1212121L);
        distributorAreaManagerDTO.setDistributorAreaName("Katraj Line");
        //when
        DistributorAreaManager distributorAreaManager = distributorAreaManagerMapper.getDistributorAreaManager(distributorAreaManagerDTO);
        //then

        assertEquals(distributorAreaManager.getId(),distributorAreaManagerDTO.getId());
        assertEquals(distributorAreaManager.getName(),distributorAreaManagerDTO.getName());
        assertEquals(distributorAreaManager.getArea().getId(),distributorAreaManagerDTO.getDistributorAreaId());
        assertEquals(distributorAreaManager.getArea().getName(),distributorAreaManagerDTO.getDistributorAreaName());

    }
}
