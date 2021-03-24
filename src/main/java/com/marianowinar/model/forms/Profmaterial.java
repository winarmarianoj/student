package com.marianowinar.model.forms;

public class Profmaterial {
	private Long personId;
	private String nameMaterial;
	
	public Profmaterial() {}

	public Profmaterial(Long personId, String nameMaterial) {
		this.personId = personId;
		this.nameMaterial = nameMaterial;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getNameMaterial() {
		return nameMaterial;
	}

	public void setNameMaterial(String nameMaterial) {
		this.nameMaterial = nameMaterial;
	}


}
