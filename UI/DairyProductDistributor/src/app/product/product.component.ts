import { Component, OnInit } from '@angular/core';
import {Product} from './product';
import {ProductService} from './product.service';
import { ProductBrandService } from '../product-brand/product-brand.service';
import { ProductBrand } from '../product-brand/product-brand';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  
  products : Product[];
  productBrands : ProductBrand[];

  searchBrandId : number = 0;;

  model = Product.getEmptyObject();

  submitted = false;


  onSubmit() { 
    this.submitted = true;
    console.log("On submit "+this.submitted);
    this.service.getAllProducts().subscribe(products => this.products = products);
  }

  // TODO: Remove this when we're done
  get diagnostic() { return JSON.stringify(this.model); }

  constructor(public service : ProductService, public brandService: ProductBrandService) {
  }

  newProduct(){
    this.model = Product.getEmptyObject();
  }

  delete(product: Product){
    this.service.deleteById(product.id).subscribe(res =>{
      let i = 0;
      var index = this.products.indexOf(product);
      this.products.splice(index, 1)
    });
  }


  ngOnInit() {
    this.service.getAllProducts().subscribe(products => {this.products = products; console.log(this.products);} );
    this.brandService.getAllProductBrands().subscribe(data => this.productBrands = data)
   }

}
