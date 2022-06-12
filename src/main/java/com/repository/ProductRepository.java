package com.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entity.Product;
import com.entity.enums.CategoryProduct;
import com.entity.enums.TypeProduct;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{
	Product findByName(String name);
	
	boolean existsByName(String name);

	List<Product> findByType(TypeProduct type);
	
	List<Product> findByCategory(CategoryProduct categorie);
}
