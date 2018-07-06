package org.product.distributor.web.rest;

import org.product.distributor.dto.DistributorAreaDTO;
import org.product.distributor.services.DistributorAreaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vikram on 05/07/18.
 */
@CrossOrigin
@RestController
@RequestMapping("api/distributor-area")
public class DistributorAreaRestController {

    private DistributorAreaService distributorAreaService;

    public DistributorAreaRestController(DistributorAreaService distributorAreaService) {
        this.distributorAreaService = distributorAreaService;
    }

    @PostMapping
    public void saveDistributorArea(@RequestBody DistributorAreaDTO distributorAreaDTO){
        distributorAreaService.saveDistributorArea(distributorAreaDTO);
    }

    @PutMapping
    public void updateDistributorArea(@RequestBody DistributorAreaDTO distributorAreaDTO){
        distributorAreaService.updateDistributorArea(distributorAreaDTO);
    }

    @GetMapping("/list")
    public List<DistributorAreaDTO> getDistributorAreas(){

        return distributorAreaService.getAll();
    }

    @GetMapping("/{id}")
    public DistributorAreaDTO getDistributorArea(@PathVariable Long id){

        return distributorAreaService.getById(id);
    }
}
