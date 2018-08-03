/**
 * 
 */
package com.social.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.social.dao.AttendeeRepository;
import com.social.dao.CommentRepository;
import com.social.dao.EventRepository;
import com.social.dao.UserRepository;
import com.social.entities.Attendee;
import com.social.entities.Comment;
import com.social.entities.Event;
import com.social.entities.User;

/**
 * @author manikanta.s
 *
 */
@Service
public class EventService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	AttendeeRepository attendeeRepository;
	
	public Event saveEvent(Event event) {
		return eventRepository.save(event);
	}
	
	public List<Event> getAllEvents() {
//		return eventRepository.findAllByState(state);
		return eventRepository.findAll();
	}

	//////////////////////////
	public Map<Long, List<User>> getAllAttendees(List<Long> eventIds) {
		Map<Long, List<User>> eventUsersMap = new HashMap<Long, List<User>>();
		for (Long eventId : eventIds) {
			List<Attendee> attendeeList = attendeeRepository.findByAttendingEvent(eventId);
			List<Long> userIds = attendeeList.stream().filter(attendee -> attendee.getStatus().equals("Y")).map(attendee -> attendee.getAttendingUser()).collect(Collectors.toList());
			eventUsersMap.put(eventId, userRepository.findAll(userIds));
		}
		return eventUsersMap;
	}
	
	public List<User> getEventAttendees(Long eventId) {
		List<Attendee> attendees = attendeeRepository.findByAttendingEvent(eventId);
		List<Long> userIds = attendees.stream().filter(attendee -> attendee.getStatus().equals("Y")).map(attendee -> attendee.getAttendingUser()).collect(Collectors.toList());
//		List<Long> userIds = attendeeList.stream().map(attendee -> attendee.getAttendingUser()).collect(Collectors.toList());
		return userRepository.findAll(userIds);
	}
	
	public List<Comment> getEventComments(Long eventId) {
		return commentRepository.findByCommentedEventId(eventId);
	}
	
	public Comment saveComment(Comment comment) {
		return commentRepository.save(comment);
	}
	
	public void deleteEvent(Event event) {
		List<Attendee> attendees = attendeeRepository.findByAttendingEvent(event.getId());
		attendeeRepository.delete(attendees);
		
		List<Comment> eventComments = getEventComments(event.getId());
		commentRepository.delete(eventComments);
		
		eventRepository.delete(event);
	}
	
	public Event updateEvent(Event event) {
		return eventRepository.saveAndFlush(event);
	}
	
	public void updateJoiningStatus(Attendee attendee) {
		List<Attendee> att = attendeeRepository.findByAttendingEvent(attendee.getAttendingEvent());
		att = att.stream().filter(attend -> attend.getAttendingUser().equals(attendee.getAttendingUser())).collect(Collectors.toList());
		
		if(!CollectionUtils.isEmpty(att)) {
			att.get(0).setStatus(attendee.getStatus());
			attendeeRepository.save(att);
		} else {
			attendeeRepository.save(attendee);
		}
	}
	
	public Event getEvent(Long eventId) {
		return eventRepository.findOne(eventId);
	}
	
}
