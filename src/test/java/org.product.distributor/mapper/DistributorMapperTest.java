package org.product.distributor.mapper;

import org.junit.Test;
import org.product.distributor.dto.DistributorDTO;
import org.product.distributor.model.Distributor;

import static org.junit.Assert.assertEquals;

/**
 * Created by vikram on 04/07/18.
 * Tests
 */
public class DistributorMapperTest {


    private DistributorMapper distributorMapper = DistributorMapper.INSTANCE;

    @Test
    public void getDistributorDTOTest(){
        //given
        Distributor distributor = new Distributor();
        distributor.setId(1L);
        distributor.setName("Sai Dairy");
        //when
        DistributorDTO distributorDTO = distributorMapper.getDistributorDTO(distributor);
        //then
        assertEquals(distributor.getId(), distributorDTO.getId());
        assertEquals(distributor.getName(), distributorDTO.getName());
    }

    @Test
    public void getDistributorTest(){
        //given
        DistributorDTO distributorDTO = new DistributorDTO();
        distributorDTO.setId(1L);
        distributorDTO.setName("Sai Dairy");
        //when
        Distributor distributor = distributorMapper.getDistributor(distributorDTO);
        //then
        assertEquals(distributorDTO.getId(), distributor.getId());
        assertEquals(distributorDTO.getName(), distributor.getName());
    }

}
