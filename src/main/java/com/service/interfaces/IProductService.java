package com.service.interfaces;

import java.util.List;

import com.entity.Product;
import com.entity.enums.CategoryProduct;
import com.entity.enums.TypeProduct;

public interface IProductService {
	Product add(Product product, String usernameUser);

	Product update(Product product);

	void delete(long id);
	
	Product retrieveByName(String name);

	List<Product> retrieveByType(TypeProduct type);
	
	List<Product> retrieveByCategory(CategoryProduct category);

	List<Product> retrieveAll();
	
	

}
