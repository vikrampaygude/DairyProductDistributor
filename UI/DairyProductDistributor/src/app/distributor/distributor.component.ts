import { Component, OnInit } from '@angular/core';
import {Distributor} from './distributor';
import {DistributorService} from './distributor.service';

@Component({
  selector: 'app-distributor',
  templateUrl: './distributor.component.html',
  styleUrls: ['./distributor.component.css']
})
export class DistributorComponent implements OnInit {
  
  distributors : Distributor[];

  model = new Distributor(0,'Vikram');

  submitted = false;

  onSubmit() { 
    this.submitted = true;
    console.log("On submit "+this.submitted);
    this.service.getAllDistributors().subscribe(distributors => this.distributors = distributors);
    console.log(this.distributors);
  }

  // TODO: Remove this when we're done
  get diagnostic() { return JSON.stringify(this.model); }

  constructor(public service : DistributorService) { 
  }

  newDistributor(){
    this.model = new Distributor(0,'Vikram');
  }

  ngOnInit() {
    this.service.getAllDistributors().subscribe(distributors => this.distributors = distributors);
   }

}
