package com.service.implementations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Event;
import com.entity.Product;
import com.entity.User;
import com.repository.EventRepository;
import com.repository.ProductRepository;
import com.repository.UserRepository;
import com.service.interfaces.IEventService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventService implements IEventService{
	@Autowired
	EventRepository eventRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Event add(Event event, String usernameUser) {
		Boolean ExistsByTitle = eventRepository.existsByTitle(event.getTitle());
		if (ExistsByTitle)
			return null;
		User user = userRepository.findByUsername(usernameUser);
		event.setUser(user);
		log.info("hatina l user");
		Set<Product> products = event.getProducts();
		Set<Product> newList = new HashSet<Product>();
		//event.setProducts(newList);
		log.info("hatina l list product fergha");

		for (Product i : products) {
			Product productExist = productRepository.findByName(i.getName());
			if (productExist != null) {
				newList.add(productExist);
			} else {
				Product prod = productRepository.save(i);
				newList.add(prod);
			}
		}
		event.setProducts(newList);
		return eventRepository.save(event);
	}

	@Override
	public Event update(Event event) {
		return eventRepository.save(event);
	}

	@Override
	public void delete(long id) {
		eventRepository.deleteById(id);	
	}

	@Override
	public Event retrieveByTitle(String title) {
		return eventRepository.findByTitle(title);
	}

	@Override
	public List<Event> retrieveAll() {
		return (List<Event>) eventRepository.findAll();
	}

	

}
