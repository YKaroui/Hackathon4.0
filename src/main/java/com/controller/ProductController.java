package com.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.entity.Product;
import com.entity.enums.CategoryProduct;
import com.entity.enums.TypeProduct;
import com.service.implementations.ProductService;
import com.utils.FileUpload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/product")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductController {
	@Autowired
	ProductService productService;

	@PostMapping("/add")
	public ResponseEntity<Product> add(@Valid @RequestPart Product product,
			@RequestParam("images") MultipartFile[] multipartFile, @RequestPart UsernameUser usernameUser) throws IOException {
		List<String> listImage = new ArrayList<>();
		product.setImages(listImage);

		for (MultipartFile file : multipartFile) {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			product.getImages().add(fileName);
			String uploadDir = "product-photos/" + product.getName();
			FileUpload.saveFile(uploadDir, fileName, file);
		}
		Product productSaved = productService.add(product, usernameUser.getUsernameUser());
		if (productSaved != null)
			return new ResponseEntity<>(productSaved, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	@PutMapping("/update")
	public ResponseEntity<Product> update(@RequestPart Product product,
			@RequestParam("images") MultipartFile[] multipartFile) throws IOException {
		List<String> listImage = new ArrayList<>();
		product.setImages(listImage);

		for (MultipartFile file : multipartFile) {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			product.getImages().add(fileName);
			String uploadDir = "product-photos/" + product.getName();
			FileUpload.saveFile(uploadDir, fileName, file);
		}

		Product productUpdated = productService.update(product);
		if (productUpdated != null)
			return new ResponseEntity<>(productUpdated, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		productService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/retrieveByName")
	public ResponseEntity<Product> retrieveByName(@RequestBody Product product) {
		Product productRetreived = productService.retrieveByName(product.getName());
		if (productRetreived != null)
			return new ResponseEntity<>(productRetreived, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/retrieveByType")
	public ResponseEntity<List<Product>> retrieveByType(@RequestBody UsernameUser usernameUser) {
		List<Product> productsRetreived = productService.retrieveByType(usernameUser.getTypeProduct());
		if (productsRetreived.size()!=0)
			return new ResponseEntity<>(productsRetreived, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/retrieveByCategory")
	public ResponseEntity<List<Product>> retrieveByCategory(@RequestBody UsernameUser usernameUser) {
		List<Product> productsRetreived = productService.retrieveByCategory(usernameUser.getCategoryProduct());
		if (productsRetreived.size()!=0)
			return new ResponseEntity<>(productsRetreived, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/retrieveAll")
	public ResponseEntity<List<Product>> retrieveAll() {
		List<Product> productsRetreived = productService.retrieveAll();
		if (productsRetreived.size()!=0)
			return new ResponseEntity<>(productsRetreived, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class UsernameUser {
	String usernameUser;
	TypeProduct typeProduct;
	CategoryProduct categoryProduct;
}
