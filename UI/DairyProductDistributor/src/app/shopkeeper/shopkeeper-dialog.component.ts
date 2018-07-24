import { Component, OnInit } from '@angular/core';
import {Shopkeeper} from './shopkeeper';
import {ShopkeeperService} from './shopkeeper.service';
import { Router, ActivatedRoute } from '@angular/router';
import { DistributorArea } from '../distributor-area/distributor-area';
import { DistributorAreaService } from '../distributor-area/distributor-area.service';

@Component({
  selector: 'app-shopkeeper-dialog',
  templateUrl: './shopkeeper-dialog.component.html',
  styleUrls: ['./shopkeeper.component.css']
})
export class ShopkeeperDialogComponent implements OnInit {
  distributorAreas : DistributorArea[];

  model = new Shopkeeper(0,null,null,null,null,null,0,0);

  submitted = false;

  onSubmit() { 
    this.submitted = true;
    console.log("onSubmit "+this.model.name);
    this.service.save(this.model).subscribe(val => this.router.navigate(['/shopkeepers']));
    
  }

  constructor(public service : ShopkeeperService, public router : Router, private route: ActivatedRoute,
    public distributorAreaService : DistributorAreaService) { 
      if(this.route.snapshot.params.id)
        service.getById(this.route.snapshot.params.id).subscribe(obj => this.model = obj);
      this.distributorAreaService.getAllDistributorAreas().subscribe(distributorAreas => this.distributorAreas = distributorAreas );
  }

  ngOnInit() { }

}
