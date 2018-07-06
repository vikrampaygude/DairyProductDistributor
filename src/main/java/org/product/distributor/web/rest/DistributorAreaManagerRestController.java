package org.product.distributor.web.rest;

import org.product.distributor.dto.DistributorAreaManagerDTO;
import org.product.distributor.services.DistributorAreaManagerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vikram on 05/07/18.
 */
@RestController
@RequestMapping("api/distributor-area-manager")
public class DistributorAreaManagerRestController {

    private DistributorAreaManagerService distributorAreaManagerService;

    public DistributorAreaManagerRestController(DistributorAreaManagerService distributorAreaManagerService) {
        this.distributorAreaManagerService = distributorAreaManagerService;
    }

    @PostMapping
    public void saveDistributorAreaManager(@RequestBody DistributorAreaManagerDTO distributorAreaManagerDTO){
        distributorAreaManagerService.saveDistributorAreaManager(distributorAreaManagerDTO);
    }

    @PutMapping
    public void updateDistributorAreaManager(@RequestBody DistributorAreaManagerDTO distributorAreaManagerDTO){
        distributorAreaManagerService.updateDistributorAreaManager(distributorAreaManagerDTO);
    }

    @GetMapping("/list")
    public List<DistributorAreaManagerDTO> getDistributorAreaManagers(){

        return distributorAreaManagerService.getAll();
    }

    @GetMapping("/{id}")
    public List<DistributorAreaManagerDTO> getDistributorAreaManager(){

        return distributorAreaManagerService.getAll();
    }



}
