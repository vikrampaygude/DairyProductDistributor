import { Component, OnInit } from '@angular/core';
import {CustomPrice} from './custom-price';
import {CustomPriceService} from './custom-price.service';

@Component({
  selector: 'app-custom-price',
  templateUrl: './custom-price.component.html',
  styleUrls: ['./custom-price.component.css']
})
export class CustomPriceComponent implements OnInit {
  
  customPrices : CustomPrice[];

  model = CustomPrice.getEmptyObject();

  submitted = false;

  onSubmit() { 
    this.submitted = true;
    console.log("On submit "+this.submitted);
    this.service.getAllCustomPrices().subscribe(customPrices => this.customPrices = customPrices);
  }

  // TODO: Remove this when we're done
  get diagnostic() { return JSON.stringify(this.model); }

  constructor(public service : CustomPriceService) { 
  }

  newCustomPrice(){
    this.model = CustomPrice.getEmptyObject();
  }

  ngOnInit() {
    this.service.getAllCustomPrices().subscribe(customPrices => {this.customPrices = customPrices; console.log(this.customPrices);} );
   }

}
