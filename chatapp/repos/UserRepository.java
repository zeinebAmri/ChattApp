package com.kth.chatapp.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kth.chatapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	//The query to find the user based on the email.
	@Query("SELECT u From User u Where u.email =?1")
	//Finds user based on email.
	public User findByEmail(String email);
	
	//The query to find the users based on the room name.
	@Query("SELECT u FROM User u JOIN u.rooms ur WHERE ur.roomName = ?1")
	//Finds users based on the room name.
	public List<User> getRoomMembers(String name);
}
