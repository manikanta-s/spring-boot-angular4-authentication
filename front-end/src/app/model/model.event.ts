import {User} from "../model/model.user";

export class Event {
    id : string;
    eventName: string="";
    date: string="";
    location: string="";
    state: string="";
    postedBy: string="";  
    postedByName: string="";  

    attendees : Array<User>=[];
    joiningStatus : string="";
  }