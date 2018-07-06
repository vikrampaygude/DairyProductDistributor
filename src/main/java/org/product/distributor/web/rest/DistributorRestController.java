package org.product.distributor.web.rest;

import org.product.distributor.dto.DistributorDTO;
import org.product.distributor.services.DistributorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vikram on 05/07/18.
 */
@CrossOrigin
@RestController
@RequestMapping("api/distributor")
public class DistributorRestController {

    private DistributorService distributorService;

    public DistributorRestController(DistributorService distributorService) {
        this.distributorService = distributorService;
    }

    @PostMapping
    public void saveDistributor(@RequestBody DistributorDTO distributorDTO){
        distributorService.saveDistributor(distributorDTO);
    }

    @PutMapping
    public void updateDistributor(@RequestBody DistributorDTO distributorDTO){
        distributorService.updateDistributor(distributorDTO);
    }

    @GetMapping("/list")
    public List<DistributorDTO> getDistributors(){

        return distributorService.getAll();
    }

    @GetMapping("/{id}")
    public DistributorDTO getDistributor(@PathVariable Long id){
        return distributorService.getById(id);
    }



}
