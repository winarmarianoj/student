package com.marianowinar.model.forms;

import java.util.List;

import com.marianowinar.model.Material;
import com.marianowinar.model.Person;

public class ShareMaterial extends Material{
	
	private int share;

	public ShareMaterial() {}

	public ShareMaterial(Long materialId, String name, String hour, String capacity, List<Person> persons,
			String subscribed, String detail, int share) {
		super(materialId, name, hour, capacity, persons, subscribed, detail);
		this.share = share;
	}

	public int getShare() {
		return share;
	}

	public void setShare(int share) {
		this.share = share;
	}
	

}
