package com.service.interfaces;

import java.util.List;

import com.entity.Event;

public interface IEventService {
	Event add(Event event, String usernameUser);

	Event update(Event event);

	void delete(long id);
	
	Event retrieveByTitle(String title);
	
	List<Event> retrieveAll();

}
