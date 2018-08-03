/**
 * 
 */
package com.social.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social.entities.Event;

/**
 * @author manikanta.s
 *
 */
public interface EventRepository extends JpaRepository<Event, Long> {
	
	public List<Event> findAllByState(String state);

}
