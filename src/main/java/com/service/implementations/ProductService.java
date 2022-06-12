package com.service.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Product;
import com.entity.User;
import com.entity.enums.CategoryProduct;
import com.entity.enums.TypeProduct;
import com.repository.ProductRepository;
import com.repository.UserRepository;
import com.service.interfaces.IProductService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductService implements IProductService{
	@Autowired
	ProductRepository productRepository;
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Product add(Product product, String usernameUser) {
		Boolean ExistsByName = productRepository.existsByName(product.getName());
		if (ExistsByName)
			return null;
		User user = userRepository.findByUsername(usernameUser);
		log.info("user found");
		product.setUser(user);
		log.info("user added");
		return productRepository.save(product);
	}
	
	@Override
	public Product update(Product product) {
		return productRepository.save(product);
	}

	@Override
	public void delete(long id) {
		productRepository.deleteById(id);
	}

	@Override
	public Product retrieveByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> retrieveByType(TypeProduct type) {
		return productRepository.findByType(type);
	}

	@Override
	public List<Product> retrieveByCategory(CategoryProduct category) {
		return productRepository.findByCategory(category);
	}

	@Override
	public List<Product> retrieveAll() {
		return (List<Product>) productRepository.findAll();
	}

	

}
