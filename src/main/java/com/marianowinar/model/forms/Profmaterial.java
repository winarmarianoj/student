package com.marianowinar.model.forms;

public class Profmaterial {
	private Long personId;
	private Long materialId;
	
	public Profmaterial() {}

	public Profmaterial(Long personId, Long materialId) {
		this.personId = personId;
		this.materialId = materialId;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	
	

}
