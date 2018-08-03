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
@Table(name="comments")
public class Comment {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	/*@OneToOne( fetch = FetchType.EAGER, targetEntity = User.class)
	private User commentedBy;	//user

	@OneToOne( fetch = FetchType.EAGER, targetEntity = Event.class)
	private Event event;	//Event
*/	
//	private Long commentedBy;	//user
	private Long commentedById;	//user
	private String commentedByUser;	//user
	private Long commentedEventId;	//Event
	private String comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCommentedById() {
		return commentedById;
	}

	public void setCommentedById(Long commentedById) {
		this.commentedById = commentedById;
	}

	public String getCommentedByUser() {
		return commentedByUser;
	}

	public void setCommentedByUser(String commentedByUser) {
		this.commentedByUser = commentedByUser;
	}

	public Long getCommentedEventId() {
		return commentedEventId;
	}

	public void setCommentedEventId(Long commentedEventId) {
		this.commentedEventId = commentedEventId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
