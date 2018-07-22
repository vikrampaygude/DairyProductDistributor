import { Component, OnInit } from '@angular/core';
import {Shopkeeper} from './shopkeeper';
import {ShopkeeperService} from './shopkeeper.service';

@Component({
  selector: 'app-shopkeeper',
  templateUrl: './shopkeeper.component.html',
  styleUrls: ['./shopkeeper.component.css']
})
export class ShopkeeperComponent implements OnInit {
  
  shopkeepers : Shopkeeper[];

  model = new Shopkeeper(0,null,null,null,null,null,0);

  submitted = false;


  onSubmit() { 
    this.submitted = true;
    console.log("On submit "+this.submitted);
    this.service.getAllShopkeepers().subscribe(shopkeepers => this.shopkeepers = shopkeepers);
  }

  // TODO: Remove this when we're done
  get diagnostic() { return JSON.stringify(this.model); }

  constructor(public service : ShopkeeperService) { 
    this.service.getAllShopkeepers().subscribe(shopkeepers => this.shopkeepers = shopkeepers);
  }

  newShopkeeper(){
    this.model = new Shopkeeper(0,null,null,null,null,null,0);
  }

  ngOnInit() {
   }

}
