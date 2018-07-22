import { Injectable } from '@angular/core';
import { CustomPrice } from './custom-price';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { OrderGridData } from '../order/order-grid';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json','Access-Control-Allow-Origin':'*' })
};

@Injectable({
  providedIn: 'root'
})
export class CustomPriceService {

  public url = 'http://localhost:8080/api/shopkeeper-custom-price';

  constructor(  private http: HttpClient) { }

  getAllCustomPrices(): Observable<CustomPrice[]> {
    return this.http.get<CustomPrice[]>(this.url+"/list");
  }

  getById(id: number): Observable<CustomPrice>{
    return this.http.get<CustomPrice>(this.url+"/"+id);
   
  }
  
  save(customPrice: CustomPrice) : Observable<OrderGridData> {
    if(customPrice.id && customPrice.id > 0)
    return this.update(customPrice);

    const copy = this.convert(customPrice);
    return this.http.post<OrderGridData>(this.url, copy);
  }

  update(customPrice: CustomPrice): Observable<OrderGridData>  {
    const copy = this.convert(customPrice);
    return this.http.put<OrderGridData>(this.url, copy);
  }

      /**
     * Convert a returned JSON object to Campaign.
     */
    private convertItemFromServer(json: any): CustomPrice {
      const entity: CustomPrice = Object.assign(CustomPrice.getEmptyObject(), json);
      return entity;
    }

      /**
     * Convert a Campaign to a JSON which can be sent to the server.
     */
    private convert(customPrice: CustomPrice): CustomPrice {
      const copy: CustomPrice = Object.assign({}, customPrice);
      return copy;
  }
  

}
