import { Injectable } from '@angular/core';
import { Product } from './product';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json','Access-Control-Allow-Origin':'*' })
};

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  public url = 'http://localhost:8080/api/product';

  constructor(  private http: HttpClient) { }

  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.url+"/list");
  }

  getById(id: number): Observable<Product>{
    return this.http.get<Product>(this.url+"/"+id);
   
  }

  getByDistributorAreaId(distributorAreaId: number): Observable<Product[]>{
    return this.http.get<Product[]>(this.url+"/distributorArea/"+distributorAreaId);
   
  }
  
  save(product: Product) {
    if(product.id && product.id > 0)
    return this.update(product);

    const copy = this.convert(product);
    return this.http.post(this.url, copy);
  }

  update(product: Product) {
    const copy = this.convert(product);
    return this.http.put(this.url, copy);
  }

      /**
     * Convert a returned JSON object to Campaign.
     */
    private convertItemFromServer(json: any): Product {
      const entity: Product = Object.assign(Product.getEmptyObject(), json);
      return entity;
    }

      /**
     * Convert a Campaign to a JSON which can be sent to the server.
     */
    private convert(product: Product): Product {
      const copy: Product = Object.assign({}, product);
      return copy;
  }
  

}
