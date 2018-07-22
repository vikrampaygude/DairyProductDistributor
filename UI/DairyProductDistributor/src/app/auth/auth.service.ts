import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Register } from '../register/register';
import { Login } from '../login/login';
import { Router } from '../../../node_modules/@angular/router';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json','Access-Control-Allow-Origin':'*' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public url = 'http://localhost:8080/api/auth';

  loggedIn : boolean = false;

  constructor(  private http: HttpClient, private router: Router) { }


  register(register: Register) {
    return this.http.post(this.url+"/register", register);
  }


  login(login: Login): any{
    return this.http.post<any>(this.url+"/login", login).subscribe(response =>{ 
      localStorage.setItem("id_token",response.access_token);
      this.router.navigate(['/orders']);

    });
  }

  logout(){
    localStorage.removeItem("id_token");
    this.router.navigate(['/login']);
    //return this.http.post(this.url+"/logout", "").subscribe(response => console.log(""));
  }

}
