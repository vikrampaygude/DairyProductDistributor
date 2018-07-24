import { Component, OnInit } from '@angular/core';
import {Order} from './order';
import {OrderService} from './order.service';
import { DistributorAreaService } from '../distributor-area/distributor-area.service';
import { DistributorArea } from '../distributor-area/distributor-area';
import { OrdersSearch } from './ordersSearch';
import { OrderGridData } from './order-grid';
import { ShopkeeperOrder } from './shopkeeper-order';
import { DatePipe } from '@angular/common';
import { ProductService } from '../product/product.service';
import { ShopkeeperService } from '../shopkeeper/shopkeeper.service';
import { NewOrder } from './new-order';
import { Shopkeeper } from '../shopkeeper/shopkeeper';
import { Product } from '../product/product';
import { ProductWeightPrice } from '../product-weight-price/product-weight-price';
import { ProductWeightPriceService } from '../product-weight-price/product-weight-price.service';
import { CustomPrice } from '../custom-price/custom-price';
import { CustomPriceService } from '../custom-price/custom-price.service';


@Component({
  selector: 'app-Order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  
  ordersSearch: OrdersSearch = new OrdersSearch(0,null);
//  orders : Order[];
  newOrder = new NewOrder();
  searched = false;
  distributorAreas : DistributorArea[];
  orderGridData : OrderGridData;
  orderPrintGridData : OrderGridData = new OrderGridData();
  orders : Object[] = new Array();
  public datePipe = new DatePipe("en-US");

  shopkeepers: Shopkeeper[];
  products : Product[];
  productWeightPrices : ProductWeightPrice[];

  toggleAddByWeight : boolean = false;
  toggleCustomPrice : boolean = false;
  customPrice : CustomPrice;
  

  onSubmit() { 
    this.searched = true;
    console.log("On submit "+this.searched);
    this.service.getDayOrders(this.ordersSearch).subscribe(data => {
        //this.orderGridData= new OrderGridData();
        this.orderGridData = data;
        console.log(this.orderGridData)
        Object.assign(this.orderPrintGridData, this.orderGridData);
      });
       this.setShopkeepers();
       this.setProducts();
    //this.service.getAllOrders().subscribe(Orders => this.Orders = Orders);
  }

  createDailyOrder(){
    console.log("Daily order click");
    this.service.createEmptyDailyOrder(this.ordersSearch).subscribe(obj => {
      console.log(obj);
      this.onSubmit();
    });
  }

  getDistributorName(): string{
    let areaName;
    if(this.distributorAreas){
      this.distributorAreas.forEach(area => {
        if(area.id == this.ordersSearch.distributorAreaId)
          areaName = area.distributorName
        
      });
    }
    return areaName;
  }

  deleteDayOrder(){
    if(confirm("Are you sure to delete this daily order?")) {
      this.service.deleteDayOrder(this.ordersSearch).subscribe(obj => {
        console.log(obj);
        this.onSubmit();
      });
    }
  }

  createOrderAsYesterday(){
    this.service.createOrderAsYesterday(this.ordersSearch).subscribe(obj => {
      this.onSubmit();
    });
  }

  copyYesterdayOrder(orderId){
    if(confirm("Are you sure to copy yesterdays order ?")) {
      this.service.copyYesterdayOrder(orderId).subscribe(data => this.orderGridData = data);
    }
  }

  customPriceFormOnSubmit(){
    console.log(this.customPrice);
    this.customPriceService.save(this.customPrice).subscribe(data => this.orderGridData = data);
    this.toggleCustomPrice = !this.toggleCustomPrice;
  }

  manageCustomPrice(order: Order, shopkeeperOrder: ShopkeeperOrder){
    
    this.customPrice = CustomPrice.getEmptyObject();
    this.customPrice.orderProductId  = order.id;
    this.customPrice.id = order.customPriceId;
    this.customPrice.productId = order.productId;
    this.customPrice.productName = order.productName;
    this.customPrice.shopkeeperOrderId = shopkeeperOrder.id;
    this.customPrice.shopkeeperName = shopkeeperOrder.shopkeeperName;
    this.customPrice.price = order.sellingPrice;
    this.customPrice.productWeightPriceId = order.productWeightPriceId;

    this.toggleCustomPrice = true;
  }

  changeDate(i : number){
    let date = new Date(this.ordersSearch.date);
    date.setDate(date.getDate() + i);
    this.ordersSearch.date = this.datePipe.transform(date, 'yyyy-MM-dd');
    this.onSubmit();
  }

  applyLatestPrices(){
    this.service.applyLatestPrices(this.ordersSearch).subscribe(
      data => {
        this.onSubmit();
      });
  }

  onChangeUpdateOrder(productOrder: Order, shopkeeperOrder: ShopkeeperOrder){
    this.service.updateQuantity(productOrder).subscribe( response => {
      //this.orderGridData = gridData;      
    });
  }

  setShopkeepers() {
    this.shopkeeperService.getAllShopkeepersByArea(this.ordersSearch.distributorAreaId).subscribe(data => this.shopkeepers = data);
  }

  setProducts(){
    this.productService.getByDistributorAreaId(this.ordersSearch.distributorAreaId).subscribe(data => this.products = data);
  }

  setProductWeightPrices(){
    this.productWeightPriceService.getProductWeightPrices(this.newOrder.productId).subscribe(data => this.productWeightPrices = data);
  }

  updatePaidOrder(shopkeeperOrderDTO : ShopkeeperOrder){
      //orderSer
      this.service.updatePaidAmount(shopkeeperOrderDTO).subscribe(gridData =>{
        this.orderGridData = gridData;
        console.log(this.orderGridData);
      });
  }

  newOrderFormOnSubmit(){
    //get the order id 
    this.orderGridData.dailySellRowDataDTOList.forEach(order=> {
      if(order.shopkeeperOrderDTO.shopkeeperId == this.newOrder.shopkeeperId){
        this.newOrder.orderId = order.shopkeeperOrderDTO.id;
        return;
      }
    });
    this.toggleAddByWeight = !this.toggleAddByWeight;
    this.service.saveNewOrder(this.newOrder).subscribe(gridData =>{
      this.orderGridData = gridData;
      this.newOrder = new NewOrder();
    });
  }
  // TODO: Remove this when we're done
//  get diagnostic() { return JSON.stringify(this.model); }

  constructor(public service : OrderService
    , public distributorAreaService : DistributorAreaService
    , public productService: ProductService
    , public shopkeeperService: ShopkeeperService
    , public productWeightPriceService: ProductWeightPriceService
    , public customPriceService : CustomPriceService) {

    this.distributorAreaService.getAllDistributorAreas().subscribe(areas => {
      this.distributorAreas = areas;
      this.setDefaultSearch();
    });
  }

  ngOnInit() {
      //this.service.getAllOrders().subscribe(Orders => {this.Orders = Orders; console.log(this.Orders);} );
   }

   //temp function remvoe later
   setDefaultSearch(){
      this.ordersSearch.date = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
      
      if(this.distributorAreas && this.distributorAreas[0])
        this.ordersSearch.distributorAreaId = this.distributorAreas[0].id;
      
      this.onSubmit();
   }


   print(): void {
    let printContents, popupWin;
    printContents = document.getElementById('ordersPrintDiv').innerHTML;
    popupWin = window.open('', '_blank', 'top=0,left=0,height=100%,width=auto');
    popupWin.document.open();
    popupWin.document.write(`
      <html>
        <head>
          <title>${this.getDistributorName()} ${this.ordersSearch.date}</title>
          <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
          <style>
          </style>
        </head>
    <body onload="window.print();window.close()">${printContents}</body>
      </html>`
    );
    popupWin.document.close();
}
}
