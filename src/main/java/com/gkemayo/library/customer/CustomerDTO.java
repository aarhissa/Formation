package com.gkemayo.library.customer;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;



@Schema(defaultValue = "Customer Model")
public class CustomerDTO implements Comparable<CustomerDTO>{
	
	@Schema(defaultValue = "Customer id")
	private Integer id;
	
	@Schema(defaultValue = "Customer first name")
	private String firstName;
	
	@Schema(defaultValue = "Customer last name")
	private String lastName;
	
	@Schema(defaultValue = "Customer job")
	private String job;
	
	@Schema(defaultValue = "Customer address")
	private String address;
	
	@Schema(defaultValue = "Customer email")
	private String email;
	
	@Schema(defaultValue = "Customer creation date in the system")
	private LocalDate creationDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public int compareTo(CustomerDTO o) {
		return this.lastName.compareToIgnoreCase(o.getLastName());
	}
		
}
