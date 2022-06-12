package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entity.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
	Event findByTitle(String title);
	
	boolean existsByTitle(String title);

}
