package com.marianowinar.service.validator;

import com.marianowinar.model.Material;
import com.marianowinar.service.exception.material.InvalidCapacityMaterialException;
import com.marianowinar.service.exception.material.InvalidNameMaterialException;

public class ValidMaterial {
	
	private static ValidMaterial validMaterial;
	
	private ValidMaterial() {}
	
	public static ValidMaterial getInstance() {
		if(validMaterial == null) validMaterial = new ValidMaterial();
		
		return validMaterial;
	}
	
	public boolean validCapacityMaterial(Material mat) throws InvalidCapacityMaterialException {
		boolean res = validCapacity(mat.getCapacity());
		if(!res)throw new InvalidCapacityMaterialException("Invalid support capacity or null");
		
		return res;
	}
	
	public boolean validNameMaterial(Material mat) throws InvalidNameMaterialException, InvalidCapacityMaterialException {
		boolean res = validName(mat.getName());
		res &= validName(mat.getHour());
		res &= validCapacityMaterial(mat);
		if(!res)throw new InvalidNameMaterialException("Invalid name material or null");
		
		return res;
	}
	
	private boolean validCapacity(int capacity) {return capacity >= 0 && capacity <= 100;}
	private boolean validName(String name) {return name != null || name != "";}	
}
