package org.product.distributor.services;

import org.product.distributor.dto.DistributorAreaDTO;
import org.product.distributor.mapper.DistributorAreaMapper;
import org.product.distributor.model.DistributorArea;
import org.product.distributor.repository.DistributorAreaRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikram on 05/07/18.
 * 
 */
@Service
public class DistributorAreaService {

    private DistributorAreaRepo distributorAreaRepo;
    private DistributorAreaMapper distributorAreaMapper;

    public DistributorAreaService(DistributorAreaRepo distributorAreaRepo, DistributorAreaMapper distributorAreaMapper) {
        this.distributorAreaRepo = distributorAreaRepo;
        this.distributorAreaMapper = distributorAreaMapper;
    }

    public List<DistributorAreaDTO> getAll(){
        List<DistributorAreaDTO> distributorAreaDTOList = new ArrayList<>();
        distributorAreaRepo.findAll().forEach(d -> distributorAreaDTOList.add(distributorAreaMapper.getDistributorAreaDTO(d)));
        return distributorAreaDTOList;
    }

    public DistributorAreaDTO getById(Long id){
        return distributorAreaMapper.getDistributorAreaDTO(distributorAreaRepo.findById(id).get());
    }

    public DistributorArea saveDistributorArea(DistributorAreaDTO distributorDTO){
        return distributorAreaRepo.save(distributorAreaMapper.getDistributorArea(distributorDTO));
    }

    public DistributorArea updateDistributorArea(DistributorAreaDTO distributorDTO){
        if(distributorDTO.getId() == null || distributorDTO.getId() ==0)
            throw new IllegalArgumentException();

        return distributorAreaRepo.save(distributorAreaMapper.getDistributorArea(distributorDTO));
    }

}
