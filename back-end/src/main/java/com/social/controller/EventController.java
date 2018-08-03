/**
 * 
 */
package com.social.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.social.entities.Attendee;
import com.social.entities.Comment;
import com.social.entities.Event;
import com.social.entities.User;
import com.social.services.EventService;

/**
 * @author manikanta.s
 *
 */
@RestController
@RequestMapping("event")
public class EventController {
	
	public static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private EventService eventService;

	// request method to create a new account by a guest
	@CrossOrigin
	@RequestMapping(value = "/createEvent", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody Event event) {
		
//		eventService.saveEvent(event);
		return new ResponseEntity<Event>(eventService.saveEvent(event), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public ResponseEntity<?> events() {
		return new ResponseEntity<List<Event>>(eventService.getAllEvents(), HttpStatus.CREATED);
	}
	
	///////////////////////////
	@RequestMapping(value = "/attendees", method = RequestMethod.POST)
	public ResponseEntity<?> attendees(@RequestBody List<Long> eventIds) {
		return new ResponseEntity<Map<Long, List<User>>>(eventService.getAllAttendees(eventIds), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/eventAttendees", method = RequestMethod.POST)
	public ResponseEntity<?> eventAttendees(@RequestBody Event event) {
		return new ResponseEntity<List<User>>(eventService.getEventAttendees(event.getId()), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/eventComments", method = RequestMethod.POST)
	public ResponseEntity<?> eventComments(@RequestBody Event event) {
		return new ResponseEntity<List<Comment>>(eventService.getEventComments(event.getId()), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	public ResponseEntity<?> addComment(@RequestBody Comment comment) {
		return new ResponseEntity<Comment>(eventService.saveComment(comment), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/deleteEvent", method = RequestMethod.POST)
	public ResponseEntity<?> deleteEvent(@RequestBody Event event) {
		eventService.deleteEvent(event);
		return new ResponseEntity<String>("Event Deleted successfully.", HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/updateEvent", method = RequestMethod.POST)
	public ResponseEntity<?> updateEvent(@RequestBody Event event) {
		return new ResponseEntity<Event>(eventService.updateEvent(event), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/updateJoiningStatus", method = RequestMethod.POST)
	public ResponseEntity<?> updateJoiningStatus(@RequestBody Attendee attendee) {
		eventService.updateJoiningStatus(attendee);
		return new ResponseEntity<Event>(eventService.getEvent(attendee.getAttendingEvent()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/eventsWithUsers", method = RequestMethod.GET)
	public ResponseEntity<?> eventsWithUsers() {
		
		List<Event> events = eventService.getAllEvents();
		
		
		return new ResponseEntity<List<Event>>(eventService.getAllEvents(), HttpStatus.CREATED);
	}


}
