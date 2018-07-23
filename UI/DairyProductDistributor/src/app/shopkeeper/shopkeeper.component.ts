import { Component, OnInit } from '@angular/core';
import {Shopkeeper} from './shopkeeper';
import {ShopkeeperService} from './shopkeeper.service';
import { DistributorAreaService } from '../distributor-area/distributor-area.service';
import { DistributorArea } from '../distributor-area/distributor-area';

@Component({
  selector: 'app-shopkeeper',
  templateUrl: './shopkeeper.component.html',
  styleUrls: ['./shopkeeper.component.css']
})
export class ShopkeeperComponent implements OnInit {
  
  shopkeepers : Shopkeeper[];
  distributorAreas : DistributorArea[];

  model = new Shopkeeper(0,null,null,null,null,null,0);

  submitted = false;
  public searchDistAreaId: any = 0;


  search() { 
    this.submitted = true;
    this.service.getAllShopkeepers(this.searchDistAreaId).subscribe(shopkeepers => this.shopkeepers = shopkeepers);
  }

  // TODO: Remove this when we're done
  get diagnostic() { return JSON.stringify(this.model); }

  constructor(public service : ShopkeeperService, public distributorAreaService: DistributorAreaService) { 
    this.distributorAreaService.getAllDistributorAreas().subscribe(data => {
      console.log(data);
      this.distributorAreas= data;
      if(this.distributorAreas && this.distributorAreas[0])
        this.searchDistAreaId = data[0].id;

        this.search();
    });
  }
  
  delete(shopkeeper: Shopkeeper){
    this.service.deleteById(shopkeeper.id).subscribe(res =>{
      let i = 0;
      var index = this.shopkeepers.indexOf(shopkeeper);
      this.shopkeepers.splice(index, 1)
    });
  }

  newShopkeeper(){
    this.model = new Shopkeeper(0,null,null,null,null,null,0);
  }

  ngOnInit() {
  }

}
