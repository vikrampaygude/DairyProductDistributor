import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { ProductAreaPrice } from './product-area-price';

@Injectable({
    providedIn: 'root'
  })  
export class ProductAreaPriceService{

    public url = localStorage.getItem('host-root')+'api/product-area-price';

    public save(productAreaPrice: ProductAreaPrice): Observable<any>{
        return this.http.post(this.url, productAreaPrice);
    }

    constructor(public http: HttpClient){

    }
}