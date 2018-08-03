import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {User} from "../../model/model.user";
import {AuthService} from "../../services/auth.service";
import {AccountService} from "../../services/account.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit {
  user: User=new User();
  errorMessage:string;
  regErrorMessage:string;
  states: Array<any>;
  constructor(private authService :AuthService, public accountService: AccountService, private router: Router) { }

  ngOnInit() {
    this.states = this.accountService.states;
  }

  login(){
    this.authService.logIn(this.user)
      .subscribe(data=>{
        this.router.navigate(['/event']);
        },err=>{
        this.errorMessage="error :  Username or password is incorrect";
        }
      )
  }

  register() {
    this.accountService.createAccount(this.user).subscribe(data => {
        this.login();  
      // this.router.navigate(['/login']);
      }, err => {
        console.log("resgistration error " + err);
        this.regErrorMessage = "error : username already exist";
      }
    )
  }

  getCountry() {
    this.accountService.getCountry(this.user).subscribe(data => {

      this.user.state = data.PostOffice[0].State;
      this.user.country = data.PostOffice[0].Country;
      // data.PostOffice[0].State
      // data.PostOffice[0].Country;

    // this.router.navigate(['/login']);
    }, err => {
      console.log(err);
      // this.errorMessage = "username already exist";
      this.regErrorMessage = "Error while fetchig PIN code data"
    }
  )
  }
}
