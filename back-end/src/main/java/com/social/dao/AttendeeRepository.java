/**
 * 
 */
package com.social.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social.entities.Attendee;

/**
 * @author manikanta.s
 *
 */
public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
	
	public List<Attendee> findByAttendingEvent(Long eventId);

}
