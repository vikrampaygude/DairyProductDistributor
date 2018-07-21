package org.product.distributor.services;

import org.product.distributor.dto.DistributorAreaManagerDTO;
import org.product.distributor.mapper.DistributorAreaManagerMapper;
import org.product.distributor.model.DistributorAreaManager;
import org.product.distributor.repository.DistributorAreaManagerRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Service
@Transactional
public class DistributorAreaManagerService {

    private DistributorAreaManagerRepo distributorAreaManagerRepo;
    private DistributorAreaManagerMapper distributorAreaManagerMapper;

    public DistributorAreaManagerService(DistributorAreaManagerRepo distributorAreaManagerRepo, DistributorAreaManagerMapper distributorAreaManagerMapper) {
        this.distributorAreaManagerRepo = distributorAreaManagerRepo;
        this.distributorAreaManagerMapper = distributorAreaManagerMapper;
    }

    public List<DistributorAreaManagerDTO> getAll(){
        List<DistributorAreaManagerDTO> distributorAreaManagerDTOList = new ArrayList<>();
        distributorAreaManagerRepo.findAll().forEach(d -> distributorAreaManagerDTOList.add(distributorAreaManagerMapper.getDistributorAreaManagerDTO(d)));
        return distributorAreaManagerDTOList;
    }

    public DistributorAreaManagerDTO getById(Long id){
        return distributorAreaManagerMapper.getDistributorAreaManagerDTO(distributorAreaManagerRepo.findById(id).get());
    }

    public DistributorAreaManager saveDistributorAreaManager(DistributorAreaManagerDTO distributorAreaManagerDTO){
        return distributorAreaManagerRepo.save(distributorAreaManagerMapper.getDistributorAreaManager(distributorAreaManagerDTO));
    }

    public DistributorAreaManager updateDistributorAreaManager(DistributorAreaManagerDTO distributorAreaManagerDTO){
        if(distributorAreaManagerDTO.getId() == null || distributorAreaManagerDTO.getId() ==0)
            throw new IllegalArgumentException();

        return distributorAreaManagerRepo.save(distributorAreaManagerMapper.getDistributorAreaManager(distributorAreaManagerDTO));
    }
}
