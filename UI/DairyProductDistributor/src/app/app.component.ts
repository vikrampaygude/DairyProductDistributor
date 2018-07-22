import { Component } from '@angular/core';
import { AuthService } from './auth/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  
  constructor(public authService: AuthService){
    localStorage.setItem('host-root', 'http://localhost:8080/');
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
