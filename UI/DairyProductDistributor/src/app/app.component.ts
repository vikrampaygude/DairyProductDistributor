import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent  implements OnInit {
  
  
  ngOnInit(): void {
    if(localStorage.getItem("id_token"))
      this.router.navigate(['/orders']);
    else
      this.router.navigate(['/login'])  

  }
  title = 'app';
  
  constructor(public authService: AuthService, public router : Router){
    localStorage.setItem('host-root', 'http://localhost:5000/');
    //localStorage.setItem('host-root', '/');
    console.log(localStorage.getItem('host-root'));
  }
  
  isLoggedin(){
    if(localStorage.getItem("id_token"))
      return true;
    return false;
  }

  logout(){
    this.authService.logout();
  }
}
