import { Component, OnInit } from '@angular/core';
import {DistributorArea} from './distributor-area';
import {DistributorAreaService} from './distributor-area.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Distributor } from '../distributor/distributor';
import { DistributorService } from '../distributor/distributor.service';
import { NotificationService } from '../notification/notification.service';

@Component({
  selector: 'app-distributor-area-dialog',
  templateUrl: './distributor-area-dialog.component.html',
  styleUrls: ['./distributor-area.component.css']
})
export class DistributorAreaDialogComponent implements OnInit {
  distributors : Distributor[];

  model = new DistributorArea(0,null,null,0);
  

  submitted = false;

  onSubmit() { 
    this.submitted = true;
    console.log("onSubmit "+this.model.name);
    //this.service.getAllDistributors().subscribe(distributors => this.distributors = distributors);
    this.service.save(this.model).subscribe(val => {
        this.router.navigate(['/distributor-areas'])
        this.notification.notifySuccess("Distributor area saved successfully!");
      }
    );
    
  }

  constructor(public service : DistributorAreaService, public router : Router, private route: ActivatedRoute,
    public distributorService : DistributorService,
    public notification: NotificationService) { 
      if(this.route.snapshot.params.id)
        service.getById(this.route.snapshot.params.id).subscribe(obj => this.model = obj);
      this.distributorService.getAllDistributors().subscribe(distributors => this.distributors = distributors );
  }

  ngOnInit() { }

}
