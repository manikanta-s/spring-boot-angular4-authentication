/**
 * 
 */
package com.social.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social.entities.Comment;

/**
 * @author manikanta.s
 *
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	public List<Comment> findByCommentedEventId(Long evetId);

}
