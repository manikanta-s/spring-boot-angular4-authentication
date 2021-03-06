import { Injectable } from '@angular/core';
import {User} from "../model/model.user";
import {Http} from "@angular/http";
import {AppComponent} from "../app.component";

@Injectable()
export class AccountService {
  states: Array<any> = ['Canada' , 'Pittsburgh','Washington' ];
  constructor(public http: Http) { }

  createAccount(user:User){
    return this.http.post(AppComponent.API_URL+'/account/register',user)
      .map(resp=>resp.json());
  }

  getCountry(user:User) {
    return this.http.post(AppComponent.API_URL+'/account/getPincodeDetails', user.pinCode)
      .map(resp=>resp.json());
  }

  getStates() {
    return this.states;
  }
}
