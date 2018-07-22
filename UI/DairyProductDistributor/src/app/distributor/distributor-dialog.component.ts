import { Component, OnInit } from '@angular/core';
import {Distributor} from './distributor';
import {DistributorService} from './distributor.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-distributor-dialog',
  templateUrl: './distributor-dialog.component.html',
  styleUrls: ['./distributor.component.css']
})
export class DistributorDialogComponent implements OnInit {
  
  model = new Distributor(0,'');

  submitted = false;

  onSubmit() { 
    this.submitted = true;
    console.log("onSubmit "+this.model.name);
    //this.service.getAllDistributors().subscribe(distributors => this.distributors = distributors);
    this.service.save(this.model).subscribe(val => this.router.navigate(['/distributors']));
    
  }

  constructor(public service : DistributorService, public router : Router, private route: ActivatedRoute) { 
      if(this.route.snapshot.params.id)
        service.getById(this.route.snapshot.params.id).subscribe(obj => this.model = obj);
  }

  ngOnInit() { }

}
