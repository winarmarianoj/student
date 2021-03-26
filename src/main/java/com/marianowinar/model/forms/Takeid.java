package com.marianowinar.model.forms;

public class Takeid {
	
	private Long num;
	private String text;
	private String nameMaterial;

	public Takeid() {}

	public Takeid(Long num, String text, String nameMaterial) {
		this.num = num;
		this.text = text;
		this.nameMaterial = nameMaterial;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getNameMaterial() {
		return nameMaterial;
	}

	public void setNameMaterial(String nameMaterial) {
		this.nameMaterial = nameMaterial;
	}

	
	
}
