import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { NotificationService } from '../notification.service';
import { Message } from '../message';

@Component({
  selector: 'wk-error',
  templateUrl: './errors.component.html',
  styleUrls: ['./errors.component.css']
})
export class ErrorsComponent implements OnInit {
  routeParams;
  public message: Message = new Message("","");
  public errorMessage : string = '';

  constructor(
    private activatedRoute: ActivatedRoute,
    private notificationService : NotificationService
  ) { 
    notificationService.notification$.subscribe(message => {
      this.message = message
      console.log(this.message);
    });
  }

  ngOnInit() {
    this.routeParams = this.activatedRoute.snapshot.queryParams;
  }

  

}
