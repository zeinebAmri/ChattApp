package com.kth.chatapp.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kth.chatapp.model.ChatRoom;

//CRud
public interface ChatRoomRepo extends JpaRepository<ChatRoom, Long> {
	//The query to find the user based on the email.
	@Query("SELECT r FROM ChatRoom r JOIN r.members rm WHERE rm.email = ?1")
	//Finds user based on email.
	public List<ChatRoom> getRooms(String email);
	
	//The query to find the Chatroom based on the roomID.
	@Query("select r from ChatRoom r where r.id = ?1")
	//Finds chatroom based on ID.
	//Deklaration
	public ChatRoom findRoomById(Long id);
}
