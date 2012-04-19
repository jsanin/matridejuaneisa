package com.jsanin.matri.server.dao;

import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Unindexed;

@SuppressWarnings("serial")
public class Invitado implements Serializable{
	@Id Long id;
	@Unindexed String name;
	@Unindexed String surname;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	
}
