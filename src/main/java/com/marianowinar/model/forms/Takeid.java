package com.marianowinar.model.forms;

public class Takeid {
	
	private Long num;
	private String text;

	public Takeid() {}

	public Takeid(Long num, String text) {
		this.num = num;
		this.text = text;
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

	
}
