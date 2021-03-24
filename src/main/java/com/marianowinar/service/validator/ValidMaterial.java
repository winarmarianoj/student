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
	
	public boolean validNameMaterial(Material mat) throws InvalidNameMaterialException, InvalidCapacityMaterialException {
		boolean res = validName(mat.getName());
		res &= validName(mat.getHour());
		res &= validName(mat.getCapacity());
		res &= validName(mat.getSubscribed());
		res &= validName(mat.getDetail());
		
		if(!res)throw new InvalidNameMaterialException("Invalid name material or null");
		
		return res;
	}	
	
	private boolean validName(String name) {return name != null || name != "";}	
}
