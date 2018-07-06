package org.product.distributor.services;

import org.product.distributor.dto.DistributorDTO;
import org.product.distributor.mapper.DistributorMapper;
import org.product.distributor.model.Distributor;
import org.product.distributor.repository.DistributorRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Service
public class DistributorService {


    private DistributorRepo distributorRepo;
    private DistributorMapper distributorMapper;

    public DistributorService(DistributorRepo distributorRepo, DistributorMapper distributorMapper) {
        this.distributorRepo = distributorRepo;
        this.distributorMapper = distributorMapper;
    }

    public List<DistributorDTO> getAll(){
        List<DistributorDTO> distributorDTOList = new ArrayList<>();
        distributorRepo.findAll().forEach(d -> distributorDTOList.add(distributorMapper.getDistributorDTO(d)));
        return distributorDTOList;
    }

    public DistributorDTO getById(Long id){
        return distributorMapper.getDistributorDTO(distributorRepo.findById(id).get());
    }

    public Distributor saveDistributor(DistributorDTO distributorDTO){
        return distributorRepo.save(distributorMapper.getDistributor(distributorDTO));
    }

    public Distributor updateDistributor(DistributorDTO distributorDTO){
        if(distributorDTO.getId() == null || distributorDTO.getId() ==0)
            throw new IllegalArgumentException();

        return distributorRepo.save(distributorMapper.getDistributor(distributorDTO));
    }

}
