import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {AccountService} from "../../services/account.service";
import { EventService } from '../../services/event.service';
import {User} from "../../model/model.user";
import {Event} from "../../model/model.event";
import {Comment} from "../../model/model.comment";
import {Router} from "@angular/router";
import {ActivatedRoute, Params} from '@angular/router';


@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class EventDetailsComponent implements OnInit {

  currentUser: User;
  event : Event;
  eventAttendees : Array<User>;
  eventComments : Array<Comment>;
  // eventComment : string;
  currUserComment : Comment = new Comment();
  constructor(private route: ActivatedRoute, public authService: AuthService, public accountService: AccountService, public eventService:EventService , public router: Router) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    // this.event = route.snapshot.params['eventOb']; // 3
  }

  ngOnInit() {
    this.event = JSON.parse(localStorage.getItem('currentEvent'));
    this.currUserComment.commentedEventId = this.event.id;
    this.currUserComment.commentedByUser = this.currentUser.fullName;
    this.currUserComment.commentById = this.currentUser.id;
    this.eventService.getEventAttendees(this.event).subscribe(data => {
          this.eventAttendees = data;
      }
    );

    // if(this.event.attendees.) {

    // }

    // this.eventAttendees = this.event.attendees;
    // console.log("new attendees list ", this.event.attendees);
    // console.log("new attendees ", this.eventAttendees.length);
    // console.log("old attendees ", this.event.attendees.length);

    this.eventService.getEventComments(this.event).subscribe(data => {
        this.eventComments = data;
      }
    );
  }

  commentOnEvent() {
    this.eventService.postCommentOnEvent(this.currUserComment).subscribe(data => {
      this.eventComments.push(data);
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