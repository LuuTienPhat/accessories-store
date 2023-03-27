package com.example.demo.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@EntityListeners(AuditingEntityListener.class)
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class User extends Base {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "lastname")
	private String lastname;

	@Column(name = "firstname")
	private String firstname;
	
	@JsonIgnore
	@Column(name="username")
	private String username;

	@JsonIgnore
	@Column(name="password")
	private String password;
	
	@Column(name="birthday")
	private LocalDate birthday;
	
	@Column(name="gender")
	private Integer gender;
	
	@Column(name="email")
	private String email;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="address")
	private String address;

	@ManyToMany(fetch = FetchType.LAZY)
	private Collection<Role> roles = new ArrayList<>();

//	@ManyToMany(fetch = FetchType.LAZY)
//	private Collection<SavedWord> enWords = new ArrayList<>();
	

	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	private List<Feedback> feedbacks = new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	private List<Order> orders = new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	private List<Invoice> invoices = new ArrayList<>();
	
	@JsonManagedReference
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	private List<UserProduct> userProducts = new ArrayList<>();

}
