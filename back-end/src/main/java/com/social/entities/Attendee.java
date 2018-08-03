/**
 * 
 */
package com.social.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author manikanta.s
 *
 */
@Entity
@Table(name="attendees")
public class Attendee {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	/*@OneToOne( fetch = FetchType.EAGER, targetEntity = User.class)
	private User attendingUser;	//user

	@OneToOne( fetch = FetchType.EAGER, targetEntity = Event.class)
	private Event attendingEvent;	//Event
*/	
	private Long attendingUser;	//user
	private Long attendingEvent;	//Event
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAttendingUser() {
		return attendingUser;
	}

	public void setAttendingUser(Long attendingUser) {
		this.attendingUser = attendingUser;
	}

	public Long getAttendingEvent() {
		return attendingEvent;
	}

	public void setAttendingEvent(Long attendingEvent) {
		this.attendingEvent = attendingEvent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

}
