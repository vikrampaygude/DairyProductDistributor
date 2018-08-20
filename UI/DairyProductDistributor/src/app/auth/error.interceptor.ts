import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';
import { NotificationService } from '../notification/notification.service';


@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthService, private notificationService: NotificationService) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError(err => {
            if (err.status === 401) {
                // auto logout if 401 response returned from api
                this.authenticationService.logout();
            }
            if(err.status == 500){
                this.notificationService.notifyError(err.error.message);
            }

            if(err.status == 405){
                this.notificationService.notifyError(err.error.message);
            }
            
            if(err && err.error){
                const error = err.error.message || err.statusText;
                return throwError(err);
            }
        }))
    }
}