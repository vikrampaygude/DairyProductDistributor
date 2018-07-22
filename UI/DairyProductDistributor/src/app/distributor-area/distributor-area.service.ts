import { Injectable } from '@angular/core';
import { DistributorArea } from './distributor-area';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json','Access-Control-Allow-Origin':'*' })
};

@Injectable({
  providedIn: 'root'
})
export class DistributorAreaService {

  public url = 'http://localhost:8080/api/distributor-area';

  constructor(  private http: HttpClient) { }

  getAllDistributorAreas(): Observable<DistributorArea[]> {
    return this.http.get<DistributorArea[]>(this.url+"/list");
  }

  getById(id: number): Observable<DistributorArea>{
    return this.http.get<DistributorArea>(this.url+"/"+id);
   
  }
  
  save(distributor: DistributorArea) {
    if(distributor.id && distributor.id > 0)
    return this.update(distributor);

    const copy = this.convert(distributor);
    return this.http.post(this.url, copy);
  }

  update(distributor: DistributorArea) {
    const copy = this.convert(distributor);
    return this.http.put(this.url, copy);
  }

      /**
     * Convert a returned JSON object to Campaign.
     */
    private convertItemFromServer(json: any): DistributorArea {
      const entity: DistributorArea = Object.assign(new DistributorArea(0,null,null,0), json);
      return entity;
    }

      /**
     * Convert a Campaign to a JSON which can be sent to the server.
     */
    private convert(distributor: DistributorArea): DistributorArea {
      const copy: DistributorArea = Object.assign({}, distributor);
      return copy;
  }
  

}
