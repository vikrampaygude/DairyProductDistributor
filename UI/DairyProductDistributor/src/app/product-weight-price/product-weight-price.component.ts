import { Component, OnInit } from '@angular/core';
import {ProductWeightPrice} from './product-weight-price';
import {ProductWeightPriceService} from './product-weight-price.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Product } from '../product/product';
import { ProductService } from '../product/product.service';

@Component({
  selector: 'app-product-weight-price',
  templateUrl: './product-weight-price.component.html',
  styleUrls: ['./product-weight-price.component.css']
})
export class ProductWeightPriceComponent implements OnInit {
  
  productWeightPrices : ProductWeightPrice[];

  model = new ProductWeightPrice(0,0,0,0,0,0,null);

  product : Product;



  onSubmit() { 
  }

  // TODO: Remove this when we're done
  get diagnostic() { return JSON.stringify(this.model); }

  
  newProductWeightPrice(){
    this.model = new ProductWeightPrice(0,0,0,0,0,0,null);
  }

  constructor(public service : ProductWeightPriceService, 
    public router : Router, 
    private route: ActivatedRoute,
    private productService: ProductService) { 
    //console.log(this.route.snapshot.params);
    this.product = Product.getEmptyObject();
 
    this.service.getProductWeightPrices(this.route.snapshot.params.productId).subscribe(productWeightPrices => {this.productWeightPrices = productWeightPrices; console.log(this.productWeightPrices);} );
    this.productService.getById(this.route.snapshot.params.productId).subscribe(product => this.product = product );

  }

  ngOnInit() {
    // this.service.getProductWeightPrices(1).subscribe(ProductWeightPrices => {this.ProductWeightPrices = ProductWeightPrices; console.log(this.ProductWeightPrices);} );
   }

}
