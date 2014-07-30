package com.letitguide.metadatatool.mongo.objects;

import com.googlecode.mjorm.annotations.Entity;
import com.googlecode.mjorm.annotations.Id;
import com.googlecode.mjorm.annotations.Property;

@Entity
public class Asistente {

	private String id;
	private String firstName;

	@Id
	@Property
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Property(field = "first_name")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
