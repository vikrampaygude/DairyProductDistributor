import { Observable} from 'rxjs';
import { from } from 'rxjs';
import { Location } from '@angular/common';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { HttpRequest } from '@angular/common/http';
import { HttpHandler } from '@angular/common/http';
import { HttpEvent } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    
    constructor(public router : Router){}

    intercept(req: HttpRequest<any>,
              next: HttpHandler): Observable<HttpEvent<any>> {


        const idToken = localStorage.getItem("id_token");
        if (idToken) {
            const cloned  = req.clone({ headers: req.headers.set("Authorization", 'Bearer ' + idToken)});
            return next.handle(cloned);
        }
        else {
            return next.handle(req);
        }
    }
}