import { Component, OnInit } from '@angular/core';
import {ProductBrand} from './product-brand';
import {ProductBrandService} from './product-brand.service';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationService } from '../notification/notification.service';

@Component({
  selector: 'app-product-brand-dialog',
  templateUrl: './product-brand-dialog.component.html',
  styleUrls: ['./product-brand.component.css']
})
export class ProductBrandDialogComponent implements OnInit {

  model = ProductBrand.getEmptyObject();
  

  submitted = false;

  onSubmit() { 
    this.submitted = true;
    console.log("onSubmit "+this.model.name);
    //this.service.getAllProductBrands().subscribe(distributors => this.distributors = distributors);
    this.service.save(this.model).subscribe(val => {
      this.router.navigate(['/product-brands'])
      this.notification.notifySuccess("Product brand saved successfully!")
    });
    
  }

  constructor(public service : ProductBrandService, public router : Router, private route: ActivatedRoute,
    public distributorService : ProductBrandService,
    public notification: NotificationService) { 
      if(this.route.snapshot.params.id)
        service.getById(this.route.snapshot.params.id).subscribe(obj => this.model = obj);
  }

  ngOnInit() { }

}
