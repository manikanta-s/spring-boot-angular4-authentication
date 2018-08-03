import { Injectable } from '@angular/core';
import {User} from "../model/model.user";
import {Event} from "../model/model.event";
import {Comment} from "../model/model.comment";
import {AppComponent} from "../app.component";
import { Http, RequestOptions} from '@angular/http';
import { Attendee } from '../model/model.attendee';

@Injectable()
export class EventService {

  constructor(public http: Http) { }

  createEvent(user:User, event:Event){
    // let request_data = new URLSearchParams();
    // request_data.set('event', event);
    // let request_option = new RequestOptions({ headers: this.headers, search: request_data});
    event.postedBy = user.id;
    event.postedByName = user.fullName;
    return this.http.post(AppComponent.API_URL+'/event/createEvent',event)
      .map(resp=>resp.json());
  }

  getAllEvents() {
    return this.http.get(AppComponent.API_URL+'/event/events')
      .map(resp=>resp.json());
  }

  getEventsWithAttendees(eventIds : Array<number>){
    return this.http.post(AppComponent.API_URL+'/event/attendees',eventIds)
    .map(resp=>resp.json());
  }

  postEventDetails(event: Event) {
    localStorage.setItem('currentEvent', JSON.stringify(event));
    // this.http.post('/event-details/',event);
    // this.event = event;
  }

  postEditEventDetails(event: Event) {
    localStorage.setItem('eventToEdit', JSON.stringify(event));
  }

  getEventAttendees(event : Event) {
    return this.http.post(AppComponent.API_URL+'/event/eventAttendees',event)
    .map(resp=>resp.json());
  }

  getEventComments(event : Event) {
    return this.http.post(AppComponent.API_URL+'/event/eventComments',event)
    .map(resp=>resp.json());
  }

  postCommentOnEvent(postComment : Comment) {
    // let headers = new Headers();
    // headers.append('Accept', 'application/json')
    // // creating base64 encoded String from user name and password
    // var base64Credential: string = btoa( event.id+ ':' + eventComment);
    // headers.append("Authorization", "Basic " + base64Credential);
    return this.http.post(AppComponent.API_URL+'/event/addComment/',postComment)
    .map(resp=>resp.json());
  }

  deleteEvent(event : Event) {
    console.log(event);
    return this.http.post(AppComponent.API_URL+'/event/deleteEvent/',event)
    .map(resp=>resp.json());
  }

  updateEvent(event : Event) {
    console.log(event);
    return this.http.post(AppComponent.API_URL+'/event/updateEvent/',event)
    .map(resp=>resp.json());
  }
  
  updateJoiningStatus(at) {
    // let request_data = new URLSearchParams();
    // request_data.set('eventid', '1');
    // request_data.set('userid', '1');
    // let request_option = new RequestOptions({ search: request_data});

    return this.http.post(AppComponent.API_URL+'/event/updateJoiningStatus',at)
    .map(resp=>resp.json());
  }

  joinEvent(event : Event, currentUser : User) {

    let at = new Attendee();
    at.attendingEvent = event.id;
    at.attendingUser = currentUser.id;
    at.status = 'Y';
    return this.updateJoiningStatus(at);
  }

  cancelEvent(event : Event, currentUser : User) {
    let at = new Attendee();
    at.attendingEvent = event.id;
    at.attendingUser = currentUser.id;
    at.status = 'N';
    return this.updateJoiningStatus(at);
  }

}