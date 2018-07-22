import { Component, OnInit } from '@angular/core';
import {DistributorArea} from './distributor-area';
import {DistributorAreaService} from './distributor-area.service';

@Component({
  selector: 'app-distributor-area',
  templateUrl: './distributor-area.component.html',
  styleUrls: ['./distributor-area.component.css']
})
export class DistributorAreaComponent implements OnInit {
  
  distributorAreas : DistributorArea[];

  model = new DistributorArea(0,null,null,0);

  submitted = false;

  onSubmit() { 
    this.submitted = true;
    console.log("On submit "+this.submitted);
    this.service.getAllDistributorAreas().subscribe(distributors => this.distributorAreas = distributors);
  }

  // TODO: Remove this when we're done
  get diagnostic() { return JSON.stringify(this.model); }

  constructor(public service : DistributorAreaService) { 
  }

  newDistributorArea(){
    this.model = new DistributorArea(0,null,null,0);
  }

  ngOnInit() {
    this.service.getAllDistributorAreas().subscribe(distributorAreas => {this.distributorAreas = distributorAreas; console.log(this.distributorAreas);} );
   }

}
