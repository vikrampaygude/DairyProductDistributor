import { Component, OnInit } from '@angular/core';
import {CustomPrice} from './custom-price';
import {CustomPriceService} from './custom-price.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Distributor } from '../distributor/distributor';
import { DistributorService } from '../distributor/distributor.service';
import { DistributorAreaService } from '../distributor-area/distributor-area.service';
import { DistributorArea } from '../distributor-area/distributor-area';
import { Shopkeeper } from '../shopkeeper/shopkeeper';
import { ShopkeeperService } from '../shopkeeper/shopkeeper.service';
import { Product } from '../product/product';
import { ProductService } from '../product/product.service';
import { ProductWeightPrice } from '../product-weight-price/product-weight-price';
import { ProductWeightPriceService } from '../product-weight-price/product-weight-price.service';

@Component({
  selector: 'app-custom-price-dialog',
  templateUrl: './custom-price-dialog.component.html',
  styleUrls: ['./custom-price.component.css']
})
export class CustomPriceDialogComponent implements OnInit {
  distributorAreas : DistributorArea[] = [];
  shopkeepers : Shopkeeper[] = [];
  products : Product[] = [];
  productWeightPrices : ProductWeightPrice[] = [];

  model = CustomPrice.getEmptyObject();
  isEdit = false;
  

  submitted = false;

  onSubmit() { 
    this.submitted = true;
    this.service.save(this.model).subscribe(val => this.router.navigate(['/custom-prices']));
    
  }

  public setShopkeepers(){
    this.shopkeeperService.getAllShopkeepersByArea(this.model.areaId).subscribe( shopkeepers => this.shopkeepers= shopkeepers);
  }



  constructor(public service : CustomPriceService, 
    public router : Router, 
    private route: ActivatedRoute,
    public distributorAreaService : DistributorAreaService,
    public shopkeeperService : ShopkeeperService,
    public productService: ProductService
  ) { 
      if(this.route.snapshot.params.id){
        service.getById(this.route.snapshot.params.id).subscribe(obj => this.model = obj);
        this.isEdit = true;
      }

    this.distributorAreaService.getAllDistributorAreas().subscribe(distributorAreas => this.distributorAreas = distributorAreas );
    productService.getAllProducts().subscribe(products => this.products = products);
  }

  ngOnInit() { }

}
