import { OnInit, Component } from "@angular/core";
import { Login } from "./login";
import { AuthService } from "../auth/auth.service";



@Component({
    selector:'login',
    templateUrl:'./login.component.html'
})
export class LoginComponent implements OnInit{

    login: Login = new Login();

    constructor(public authServie: AuthService){

    }

    logout() {
        localStorage.removeItem("id_token");
        localStorage.removeItem("expires_at");
    }

    ngOnInit(): void {
      //  throw new Error("Method not implemented.");

    }

    onSubmit(){
        this.authServie.login(this.login);        
    }

}
