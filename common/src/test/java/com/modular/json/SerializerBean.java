package com.modular.json;

import java.time.LocalDate;

public class SerializerBean {

	private String name;
	private long id;
	private LocalDate date;

	public SerializerBean() {

	}

	public SerializerBean(long id, String name, LocalDate date) {
		this.name = name;
		this.id = id;
		this.date = date;
	}

	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
