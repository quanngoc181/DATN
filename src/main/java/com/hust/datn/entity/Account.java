package com.hust.datn.entity;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "ACCOUNT")
public class Account extends ParentEntity {
	private String username;
	
	@Nationalized
	private String firstName;
	
	@Nationalized
	private String lastName;

	public Account() {
		super();
	}

	public Account(UUID id, String username, String firstName, String lastName) {
		super.setId(id);
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
}
