import { Injectable } from '@angular/core';
import { ProductBrand } from './product-brand';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json','Access-Control-Allow-Origin':'*' })
};

@Injectable({
  providedIn: 'root'
})
export class ProductBrandService {

  public url = 'http://localhost:8080/api/product-brand';

  constructor(  private http: HttpClient) { }

  getAllProductBrands(): Observable<ProductBrand[]> {
    return this.http.get<ProductBrand[]>(this.url+"/list");
  }

  getById(id: number): Observable<ProductBrand>{
    return this.http.get<ProductBrand>(this.url+"/"+id);
   
  }
  
  save(productBrand: ProductBrand) {
    if(productBrand.id && productBrand.id > 0)
    return this.update(productBrand);

    const copy = this.convert(productBrand);
    return this.http.post(this.url, copy);
  }

  update(productBrand: ProductBrand) {
    const copy = this.convert(productBrand);
    return this.http.put(this.url, copy);
  }

      /**
     * Convert a returned JSON object to Campaign.
     */
    private convertItemFromServer(json: any): ProductBrand {
      const entity: ProductBrand = Object.assign(ProductBrand.getEmptyObject(), json);
      return entity;
    }

      /**
     * Convert a Campaign to a JSON which can be sent to the server.
     */
    private convert(productBrand: ProductBrand): ProductBrand {
      const copy: ProductBrand = Object.assign({}, productBrand);
      return copy;
  }
  

}
