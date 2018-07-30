import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { Message } from './message';




@Injectable({
    providedIn: 'root'
})
export class NotificationService {

  private _notification: BehaviorSubject<Message> = new BehaviorSubject(null);
  readonly notification$: Observable<Message> = this._notification.asObservable();//.publish().refCount();

  constructor() {}

  notify(message) {
    this._notification.next(new Message(message,'INFO'));
    setTimeout(() => this._notification.next(null), 6000);
  }

  notifyError(message){
    this._notification.next(new Message(message, 'ERROR'));
    setTimeout(() => this._notification.next(null), 12000);
  }

  notifySuccess(message){
    this._notification.next(new Message(message, 'SUCCESS'));
    setTimeout(() => this._notification.next(null), 6000);
  }

}