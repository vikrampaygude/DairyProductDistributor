import { Injectable } from '@angular/core';
import { Order } from './order';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { OrdersSearch } from './ordersSearch';
import { DatePipe } from '@angular/common';
import { OrderGridData } from './order-grid';
import { ShopkeeperOrder } from './shopkeeper-order';
import { NewOrder } from './new-order';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json','Access-Control-Allow-Origin':'*' })
};

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  public url = localStorage.getItem('host-root')+'api/order';

  public datePipe = new DatePipe("en-US");

  constructor(  private http: HttpClient) { }

  getAllOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(this.url+"/list");
  }

  getById(id: number): Observable<Order>{
    return this.http.get<Order>(this.url+"/"+id);
   
  }

  getDayOrders(ordersSearch : OrdersSearch): Observable<OrderGridData>{
    let dateStr = this.datePipe.transform(ordersSearch.date, 'dd/MM/yyyy');

    let parameters = { params: new HttpParams().set('date', dateStr).set("distributorAreaId", ordersSearch.distributorAreaId+"") };
    return this.http.get<OrderGridData>(this.url+"/day-orders", parameters);
    
  }

  deleteDayOrder(ordersSearch : OrdersSearch){
    let dateStr = this.datePipe.transform(ordersSearch.date, 'dd/MM/yyyy');

    let parameters = { params: new HttpParams().set('date', dateStr).set("distributorAreaId", ordersSearch.distributorAreaId+"") };
    return this.http.delete(this.url+"/day-orders", parameters);
    
  }


  createEmptyDailyOrder(ordersSearch : OrdersSearch){
    const ordersSearchCopy: OrdersSearch = Object.assign({}, ordersSearch);
    ordersSearchCopy.date = this.datePipe.transform(ordersSearchCopy.date, 'dd/MM/yyyy');
    return this.http.post(this.url+"/place-day-orders", ordersSearchCopy);
  }

  createOrderAsYesterday(ordersSearch : OrdersSearch){
    const ordersSearchCopy: OrdersSearch = Object.assign({}, ordersSearch);
    ordersSearchCopy.date = this.datePipe.transform(ordersSearchCopy.date, 'dd/MM/yyyy');
    return this.http.post(this.url+"/create-yesterday-copy", ordersSearchCopy);
  }

  copyYesterdayOrder(orderId : number): Observable<OrderGridData>{
    return this.http.get<OrderGridData>(this.url+"/copy-yesterday-order/"+orderId);
  }
  
  ///apply-latest-price

  applyLatestPrices(ordersSearch : OrdersSearch){
    
    const ordersSearchCopy: OrdersSearch = Object.assign({}, ordersSearch);
    ordersSearchCopy.date = this.datePipe.transform(ordersSearchCopy.date, 'dd/MM/yyyy');
    return this.http.put(this.url+"/apply-latest-price", ordersSearchCopy);
  }


  updateQuantity(order: Order) {
    return this.http.post(this.url+"/update-quantity", order);
  }

  updatePaidAmount(shopkeeperOrder: ShopkeeperOrder): Observable<OrderGridData> {
    return this.http.post<OrderGridData>(this.url+"/update-paid-amount", shopkeeperOrder);
  }


  save(Order: Order) {
    if(Order.id && Order.id > 0)
    return this.update(Order);

    const copy = this.convert(Order);
    return this.http.post(this.url, copy);
  }

  saveNewOrder(newOrder: NewOrder):Observable<OrderGridData>{
    return this.http.post<OrderGridData>(this.url+"/by-weight", newOrder);
  }

  update(Order: Order) {
    const copy = this.convert(Order);
    return this.http.put(this.url, copy);
  }

      /**
     * Convert a returned JSON object to Campaign.
     */
    private convertItemFromServer(json: any): Order {
      const entity: Order = Object.assign(Order.getEmptyObject(), json);
      return entity;
    }

      /**
     * Convert a Campaign to a JSON which can be sent to the server.
     */
  private convert(Order: Order): Order {
      const copy: Order = Object.assign({}, Order);
      return copy;
  }

}
