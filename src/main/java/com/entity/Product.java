package com.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.entity.enums.CategoryProduct;
import com.entity.enums.TypeProduct;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Table(name = "PRODUCT", uniqueConstraints = { @UniqueConstraint(name = "Product_name_unique", columnNames = "name") })
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(name = "NAME", nullable = false)
	@NotBlank(message = "Name required")
	String name;
	@ElementCollection
	@JsonIgnore
	List<String> images;
	@Column(name = "DESCRIPTION", columnDefinition = "Text", nullable = false)
	@NotBlank(message = "Description required")
	String description;
	@Column(name = "TYPE", nullable = false)
	@NotNull(message = "Type Product required")
	@Enumerated(EnumType.STRING)
	TypeProduct type;
	@Column(name = "CATEGORY", nullable = false)
	@NotNull(message = "Category required")
	@Enumerated(EnumType.STRING)
	CategoryProduct category;
	@Column(name = "PRICE", nullable = false)
	@NotNull(message = "Price required")
	float price;
	@ManyToOne()
	@JsonIgnore
	User user;
	


}
