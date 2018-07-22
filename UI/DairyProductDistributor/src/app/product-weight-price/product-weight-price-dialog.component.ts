import { Component, OnInit } from '@angular/core';
import {ProductWeightPrice} from './product-weight-price';
import { Router, ActivatedRoute } from '@angular/router';
import { ProductWeightPriceService } from './product-weight-price.service';
import { Product } from '../product/product';
import { ProductService } from '../product/product.service';

@Component({
  selector: 'app-product-weight-price-dialog',
  templateUrl: './product-weight-price-dialog.component.html',
  styleUrls: ['./product-weight-price.component.css']
})
export class ProductWeightPriceDialogComponent implements OnInit {

  model = new ProductWeightPrice(0,0,0,0,0,0,null);
  product : Product;

  onSubmit() { 
    this.model.productId = this.product.id;
    this.service.save(this.model).subscribe(val => this.router.navigate(['/product/'+this.product.id+'/weight-price']));
  }

  constructor(public service : ProductWeightPriceService,
    public router : Router,
    private route: ActivatedRoute,
    public productService: ProductService) { 

    this.product = Product.getEmptyObject();
    // if id is present then its edit 
    if(this.route.snapshot.params.id)
      service.getById(this.route.snapshot.params.id).subscribe(obj => this.model = obj);

    //get the product object
    productService.getById(this.route.snapshot.params.productId).subscribe(obj => this.product = obj);
  }

  ngOnInit() {}

}
