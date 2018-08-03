/**
 * 
 */
package com.social.entities;

import java.util.Date;

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
@Table(name="events")
public class Event {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	private String eventName;
	private String description;
	private Date date;
	private String state;
	private String location;
	private Long postedBy;
	private String postedByName;
	
//	private Long postedBy;	//user
	
	/*@ManyToOne( fetch = FetchType.EAGER, targetEntity = User.class)
	@JoinTable(name="users",
    joinColumns={@JoinColumn(name="posted_by", referencedColumnName="id")},
    inverseJoinColumns={@JoinColumn(name="id", referencedColumnName="id")})
	private User user;	//user
*//*
	@ManyToOne( fetch = FetchType.EAGER, targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinTable(name="users",joinColumns={@JoinColumn(name="id", referencedColumnName="id")})
	private User user;	//user
*/	
	/*
	
	@ManyToOne(cascade=CascadeType.ALL)
    @JoinTable(name="attendees",
    joinColumns={@JoinColumn(name="attending_event", referencedColumnName="id")},
    inverseJoinColumns={@JoinColumn(name="attending_user", referencedColumnName="id")})
    private User postedBy;*/
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(Long postedBy) {
		this.postedBy = postedBy;
	}
/*
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}*/

	public String getPostedByName() {
		return postedByName;
	}

	public void setPostedByName(String postedByName) {
		this.postedByName = postedByName;
	}
}
