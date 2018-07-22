import { OnInit, Component } from "@angular/core";
import { Register } from "./register";
import { AuthService } from "../auth/auth.service";

@Component({
    selector:'register',
    templateUrl:'./register.component.html'
})
export class RegisterComponent implements OnInit{

    register: Register = new Register();

    constructor(public authServie: AuthService){

    }

    ngOnInit(): void {
      //  throw new Error("Method not implemented.");

    }

    onSubmit(){
        console.log(this.register);
        this.authServie.register(this.register).subscribe(response => console.log("User registered successfully"));
        
    }

}
