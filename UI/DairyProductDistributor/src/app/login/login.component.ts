import { OnInit, Component } from "@angular/core";
import { Login } from "./login";
import { AuthService } from "../auth/auth.service";
import { Router } from "@angular/router";

@Component({
    selector:'login',
    templateUrl:'./login.component.html'
})
export class LoginComponent implements OnInit{

    login: Login = new Login();
    loading : boolean = false;
    message : string;
    constructor(public authServie: AuthService, private router: Router){

    }

    logout() {
        localStorage.removeItem("id_token");
        localStorage.removeItem("expires_at");
    }

    ngOnInit(): void {
      //  throw new Error("Method not implemented.");

    }

    onSubmit(){
        this.loading = true;

        this.authServie.login(this.login).subscribe(response =>{ 
            localStorage.setItem("id_token",response.access_token);
            this.router.navigate(['/orders']);
            this.loading = false;
        },
        errResp => {
            this.loading = false;
            this.message = errResp.error.details;
            console.log(this.message)
            setTimeout(() => this.message = null, 12000);
       });
    }
}