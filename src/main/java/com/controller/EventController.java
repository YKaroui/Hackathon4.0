package com.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
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
import lombok.extern.slf4j.Slf4j;
@Slf4j
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
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class Username {
	String usernameUser;
	
}
