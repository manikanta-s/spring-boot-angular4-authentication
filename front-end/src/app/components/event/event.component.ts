import { Component, OnInit, ViewEncapsulation, ChangeDetectorRef } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {AccountService} from "../../services/account.service";
import { EventService } from '../../services/event.service';
import {User} from "../../model/model.user";
import {Event} from "../../model/model.event";
import {Router} from "@angular/router";
import { map } from 'rxjs/operator/map';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class EventComponent implements OnInit {
  states: Array<any>;
  onStateEvents: Array<Event>;
  offStateEvents: Array<Event>;
  eventIds : Array<number>;
  currentUser: User;
  event: Event = new Event();
  eventMessage: string;
  eventsWithAttendees : Map<number, Array<User>> = new Map();
  currDate : Date = new Date();
  minDate : string;
// console.log(obj.a); // 1
// console.log(obj['key']); // "value"
  constructor(private cd : ChangeDetectorRef, public authService: AuthService, public accountService: AccountService, public eventService:EventService , public router: Router) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }

  ngOnInit() {
    console.log("curr date ddd", this.currDate);
    this.minDate = this.currDate.getDate()+"-"+this.currDate.getUTCMonth()+"-"+this.currDate.getFullYear();
    console.log("curr date ", this.minDate);
    this.states = this.accountService.states;
    localStorage.setItem('states', JSON.stringify(this.states));
    this.getAllEvents();
  }

  // login out from the app
  logOut() {
    this.authService.logOut()
      .subscribe(
        data => {
          this.router.navigate(['/login']);
        },
        error => {

        });
  }

  createEvent() {
    this.eventService.createEvent(this.currentUser, this.event).subscribe(data => {
      this.eventMessage = "Event Created Successfully";
      if (data.state == this.currentUser.state) {
        this.onStateEvents.push(data);
      } else {
        this.offStateEvents.push(data);
      }
    }, err => {
      console.log("Event creation error " + err);
      // this.regErrorMessage = "error : username already exist";
    }
    )
  }



  ///checl tis
  getEventsWithAttendees(eventIds) {
    this.eventService.getEventsWithAttendees(eventIds).subscribe(data => {
      console.log("Events with Attendees map ", data);
      this.eventsWithAttendees = data;

      for (let [key, value] of Object.entries(this.eventsWithAttendees)) {
        for (let onEv of this.onStateEvents) {
          if (onEv.id == key && value != '') {
            onEv.attendees = value;
            for(let attendee of onEv.attendees) {
              if(attendee.fullName == this.currentUser.fullName) {
                onEv.joiningStatus = "Joining";
                break;
              }
            }
          }
        }

        for (let ev of this.offStateEvents) {
          if (ev.id == key && value != '') {
            ev.attendees = value;
            for(let attendee of ev.attendees) {
              if(attendee.fullName == this.currentUser.fullName) {
                ev.joiningStatus = "Joining";
                break;
              }
            }
          }
        }
      }
    })
  }

  getAllEvents() {
    this.eventService.getAllEvents().subscribe(data => {
      this.onStateEvents = [];
      this.offStateEvents = [];
      this.eventIds = [];

      for(let event of data) { 
        this.eventIds.push(event.id);
        if(event.state == this.currentUser.state) {
          this.onStateEvents.push(event);
        } else {
          this.offStateEvents.push(event);
        }
      }
      console.log("event ids", this.eventIds);

      this.getEventsWithAttendees(this.eventIds);

      // let idList = data.filter(event => event.id).list;
      // console.log(data, "data", idList, "events id list");
      }, err => {
        console.log("Error while fetching events list" + err);
        // this.regErrorMessage = "error : username already exist";
      }
    )
  }

  eventDetails(event) {
    this.eventService.postEventDetails(event);
    this.router.navigate(['/event-details']);
  }

  editEvent(event) {
    this.eventService.postEditEventDetails(event);
    this.router.navigate(['/event-edit']);
    // this.eventService.editEvent(event).subscribe(data => {
    //   console.log(data);
    // });
  }

  deleteEvent(event) {
    this.eventService.deleteEvent(event).subscribe(data => {
      console.log(data);
    });

    let ind = this.onStateEvents.indexOf(event);
      this.onStateEvents.splice(ind, ind+1);
      console.log(ind);

    ind = this.offStateEvents.indexOf(event);
      this.offStateEvents.splice(ind, ind+1);
      console.log(ind);
 
  }

  joinEvent(event) {
    event.joiningStatus = 'Joining';
    this.eventService.joinEvent(event, this.currentUser).subscribe(data => {
      console.log(data);
      event = data;
    });
  }

  cancelEvent(event, state) {
    // event.joiningStatus='';
    /////////////////// add loader here
    this.eventService.cancelEvent(event, this.currentUser).subscribe(data => {
      event = data;
      if(state == 'on') {
        let ind = this.onStateEvents.findIndex(ev => ev.id == event.id);
        this.onStateEvents[ind] = event;

      } else {
        let ind = this.offStateEvents.findIndex(ev => ev.id == event.id);
        this.offStateEvents[ind] = event;
      }

    });
    this.cd.detectChanges();
  }

  isEmpty(joiningStatus : string) {
    if(joiningStatus == undefined)
      return true;
    return false;
  }
}