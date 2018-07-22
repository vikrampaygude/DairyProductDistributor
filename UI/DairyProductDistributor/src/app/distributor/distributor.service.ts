import { Injectable } from '@angular/core';
import { Distributor } from './distributor';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json','Access-Control-Allow-Origin':'*' })
};

@Injectable({
  providedIn: 'root'
})
export class DistributorService {

  public url = 'http://localhost:8080/api/distributor';

  constructor(  private http: HttpClient) { }

  getAllDistributors(): Observable<Distributor[]> {
    return this.http.get<Distributor[]>(this.url+"/list");
  }

  getById(id: number): Observable<Distributor>{
    return this.http.get<Distributor>(this.url+"/"+id);
   
  }
  
  save(distributor: Distributor) {
    if(distributor.id && distributor.id > 0)
    return this.update(distributor);

    const copy = this.convert(distributor);
    return this.http.post(this.url, copy);
  }

  update(distributor: Distributor) {
    const copy = this.convert(distributor);
    return this.http.put(this.url, copy);
  }

      /**
     * Convert a returned JSON object to Campaign.
     */
    private convertItemFromServer(json: any): Distributor {
      const entity: Distributor = Object.assign(new Distributor(0,''), json);
      return entity;
    }

      /**
     * Convert a Campaign to a JSON which can be sent to the server.
     */
    private convert(distributor: Distributor): Distributor {
      const copy: Distributor = Object.assign({}, distributor);
      return copy;
  }
  

}
