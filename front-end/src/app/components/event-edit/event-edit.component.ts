import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { EventService } from '../../services/event.service';
import {AuthService} from "../../services/auth.service";
import {User} from "../../model/model.user";
import {Event} from "../../model/model.event";
import {Router} from "@angular/router";

@Component({
  selector: 'app-event-edit',
  templateUrl: './event-edit.component.html',
  styleUrls: ['./event-edit.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class EventEditComponent implements OnInit {
  states: Array<any>;
  currentUser: User;
  event : Event;
  eventMessage : string="";
  eventHeader : string="";
  constructor(public eventService:EventService, public authService: AuthService,public router: Router ) { 
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }

  ngOnInit() {
    this.event = JSON.parse(localStorage.getItem('eventToEdit'));
    this.states = JSON.parse(localStorage.getItem('states'));
    this.eventHeader = this.event.eventName;
    console.log("states" , this.states);
  }

  updateEvent(){
    this.eventService.updateEvent(this.event).subscribe(data => {
      this.eventMessage = "Event updated succesfully."
      console.log(data);
    });
  }

  logOut() {
    this.authService.logOut()
      .subscribe(
        data => {
          this.router.navigate(['/login']);
        },
        error => {

        });
  }


}
