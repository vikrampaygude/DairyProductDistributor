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
import { NotificationService } from '../notification/notification.service';
import { ProductAreaPrice } from './product-area-price';
import { ProductAreaPriceService } from './product-area-price.service';
import { OrderGridRowdata } from './order-grid-rowdata';


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
  prodAreaPrice: ProductAreaPrice;

  toggleAddByWeight : boolean = false;
  toggleCustomPrice : boolean = false;
  toggleAreaPrice : boolean = false;
  
  customPrice : CustomPrice;
  loading : boolean = false;
  headerCount: number =11;

  onSubmit() { 
    this.loading = true;
    this.service.getDayOrders(this.ordersSearch).subscribe(data => {
        //this.orderGridData= new OrderGridData();
        this.orderGridData = data;
        Object.assign(this.orderPrintGridData, this.orderGridData);
        this.loading = false;
        this.searched = true;
        this.addLineSeparator();
      });
       this.setShopkeepers();
       this.setProducts();
    //this.service.getAllOrders().subscribe(Orders => this.Orders = Orders);
  }

  addLineSeparator(){
    var i = 0;
    let addLineAt = [];
    if(this.orderGridData == null) return;

    for(let shopkeeperOrder of this.orderGridData.dailySellRowDataDTOList){

      if(shopkeeperOrder.shopkeeperOrderDTO.rowSeparator){
        addLineAt.push(i+1);
      }          
      i++;
    }
    for(let num of addLineAt){
      this.orderGridData.dailySellRowDataDTOList.splice(num, 0, OrderGridRowdata.getInstance());//add empty instance
    }
    this.headerCount = this.orderGridData.productDTOS.length + 4;
  }

  createDailyOrder(){
    this.service.createEmptyDailyOrder(this.ordersSearch).subscribe(obj => {
      this.onSubmit();
      this.notificationService.notifySuccess("Todays order created successfully !");

    });
  }

  getDistributorName(): string{
    let areaName;
    if(this.distributorAreas){
      this.distributorAreas.forEach(area => {
        if(area.id == this.ordersSearch.distributorAreaId)
          areaName = area.name
        
      });
    }
    return areaName;
  }

  showAreaPrice(product: Product){
    console.log(product);
    this.prodAreaPrice = ProductAreaPrice.getInstance();
    this.prodAreaPrice.id = null;
    this.prodAreaPrice.productId = product.id;
    this.prodAreaPrice.productName = product.name;
    this.prodAreaPrice.areaId = this.ordersSearch.distributorAreaId;
    this.prodAreaPrice.price = product.sellingPrice;

    this.toggleAreaPrice = true;
    // = new ProductAreaPrice(null,product.id, this.ordersSearch.distributorAreaId, );
  }

  areaPriceFormOnSubmit(){

    this.prodAreaPrice.date = this.datePipe.transform(this.ordersSearch.date, 'dd/MM/yyyy');;
    this.productAreaPriceService.save(this.prodAreaPrice).subscribe(res => {
      this.notificationService.notifySuccess("Price saved!")
      this.prodAreaPrice = ProductAreaPrice.getInstance();
    });   
    this.toggleAreaPrice = false;
  }

  deleteDayOrder(){
    if(confirm("Are you sure to delete this daily order?")) {
      this.service.deleteDayOrder(this.ordersSearch).subscribe(obj => {
        console.log(obj);
        this.onSubmit();
        this.notificationService.notifySuccess("Order deleted successfully !");

      });
    }
  }

  createOrderAsYesterday(){
    this.service.createOrderAsYesterday(this.ordersSearch).subscribe(obj => {
      this.onSubmit();
      this.notificationService.notifySuccess("Order created as per yesterday !");
    });
  }

  copyYesterdayOrder(orderId){
    if(confirm("Are you sure to copy yesterdays order ?")) {
      this.service.copyYesterdayOrder(orderId).subscribe(data => this.orderGridData = data);
    }
  }

  customPriceFormOnSubmit(){
    console.log(this.customPrice);
    this.customPriceService.save(this.customPrice).subscribe(data => {
      this.orderGridData = data
      this.notificationService.notifySuccess("Custom price applied !");
    });
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
    this.loading = true;
    this.service.applyLatestPrices(this.ordersSearch).subscribe(
      data => {
        this.onSubmit();
        this.loading = true;
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
      this.notificationService.notifySuccess("Order added successfully !");
    });
  }
  // TODO: Remove this when we're done
//  get diagnostic() { return JSON.stringify(this.model); }

  constructor(public service : OrderService
    , public distributorAreaService : DistributorAreaService
    , public productService: ProductService
    , public shopkeeperService: ShopkeeperService
    , public productWeightPriceService: ProductWeightPriceService
    , public customPriceService : CustomPriceService
    , public notificationService: NotificationService
    , public productAreaPriceService: ProductAreaPriceService) {

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

   getProductPrice(product: Product){
    let price = product.sellingPrice;
    if(product.productAreaPriceDTOList) {
      this.orderGridData.dailySellRowDataDTOList.forEach(row =>{
        if(row.orderProductDTOS){  
          row.orderProductDTOS.forEach(orderProduct=>{
            if(product.id == orderProduct.productId 
              && orderProduct.productWeightPriceId == null 
              && orderProduct.customPriceId ==null){
                price = orderProduct.sellingPrice;
              }
          })
        }
      });
    }  
    return price;
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
          <style>
      @media all {    
        table {
            font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }
        
        table td, th {
            border: 1px solid #dee2e6;
            padding: 8px;
        }
        
        table tr:nth-child(even){background-color: #f2f2f2;}
        
        table th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: left;
            background-color: #e9ecef;
        }
      }
        
        </style>
        </head>
    <body onload="window.print();window.close()">${printContents}</body>
      </html>`
    );
    popupWin.document.close();
}
}
