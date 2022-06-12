package com.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.entity.Event;
import com.service.implementations.EventService;
import com.utils.FileUpload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/event")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventController {
	@Autowired
	EventService eventService;
	
	@PostMapping("/add")
	public ResponseEntity<Event> add(@Valid @RequestPart Event event,
			@RequestPart Username username, @RequestParam("image") MultipartFile multipartFile) throws IOException {
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		event.setImage(fileName);
		String uploadDir = "product-photos/" + event.getTitle();
		FileUpload.saveFile(uploadDir, fileName, multipartFile);
		Event eventSaved = eventService.add(event, username.getUsernameUser());
		if (eventSaved != null)
			return new ResponseEntity<>(eventSaved, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		eventService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/retrieveByTitle")
	public ResponseEntity<Event> retrieveByTitle(@RequestBody Event event) {
		Event eventRetreived = eventService.retrieveByTitle(event.getTitle());
		if (eventRetreived != null)
			return new ResponseEntity<>(eventRetreived, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/retrieveAll")
	public ResponseEntity<List<Event>> retrieveAll() {
		List<Event> eventsRetreived = eventService.retrieveAll();
		if (eventsRetreived.size()!=0)
			return new ResponseEntity<>(eventsRetreived, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class Username {
	String usernameUser;
	
}
