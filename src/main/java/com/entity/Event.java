package com.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Table(name = "EVENT", uniqueConstraints = { @UniqueConstraint(name = "Event_title_unique", columnNames = "title") })
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(name = "TITLE", nullable = false)
	@NotBlank(message = "Title required")
	String title;
	@Column(name = "START_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	Date startDate;
	@Column(name = "END_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	Date endDate;
	@Column(name = "OBJECTIVE", nullable = false)
	@NotNull(message = "Objective required")
	float objective;
	@Column(name = "ACHIEVED", nullable = false)
	float achieved = 0;
	String image;
	@ManyToOne()
	@JsonIgnore
	User user;
	@ManyToMany(fetch = FetchType.EAGER)
	Set<Product> products;

}
