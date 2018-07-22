
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ProductWeightPrice } from './product-weight-price';
import { Observable, of } from 'rxjs';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json','Access-Control-Allow-Origin':'*' })
};

@Injectable({
  providedIn: 'root'
})
export class ProductWeightPriceService {

  public url = localStorage.getItem('host-root')+'api/product-weight-price';

  constructor(  private http: HttpClient) { }

  getProductWeightPrices(productId: number): Observable<ProductWeightPrice[]> {
    return this.http.get<ProductWeightPrice[]>(this.url+"/"+productId+"/list");
  }

  getById(id: number): Observable<ProductWeightPrice>{
    return this.http.get<ProductWeightPrice>(this.url+"/"+id);
   
  }
  
  save(ProductWeightPrice: ProductWeightPrice) {
    if(ProductWeightPrice.id && ProductWeightPrice.id > 0)
    return this.update(ProductWeightPrice);

    const copy = this.convert(ProductWeightPrice);
    return this.http.post(this.url, copy);
  }

  update(ProductWeightPrice: ProductWeightPrice) {
    const copy = this.convert(ProductWeightPrice);
    return this.http.put(this.url, copy);
  }

      /**
     * Convert a returned JSON object to Campaign.
     */
    private convertItemFromServer(json: any): ProductWeightPrice {
      const entity: ProductWeightPrice = Object.assign(new ProductWeightPrice(0,0,0,0,0,0,null), json);
      return entity;
    }

      /**
     * Convert a Campaign to a JSON which can be sent to the server.
     */
    private convert(ProductWeightPrice: ProductWeightPrice): ProductWeightPrice {
      const copy: ProductWeightPrice = Object.assign({}, ProductWeightPrice);
      return copy;
  }
  

}
