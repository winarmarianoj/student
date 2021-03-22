package com.marianowinar.service.logger;

import java.util.ArrayList;
import java.util.List;

public class ListErrors {
	
	private List<String> listErrors;

	// METHODS AND FUNCTION GAME LIST
	
	public List<String> getListErrors() {
        if (listErrors == null){listErrors = new ArrayList<>();}
        return listErrors;
    }

    public void addErrors(String err) {
    	if (listErrors == null){listErrors = new ArrayList<>();}
        listErrors.add(err);
    }  
    public int listaErrorsize(){
    	if (listErrors == null){listErrors = new ArrayList<>();}
        return listErrors.size();
    }
    public String searchErrors(int index){
        if (index < 0 || index >= listaErrorsize()){return null;}
        return listErrors.get(index);
    }
    public boolean removeErrors(int index){
    	if (index < 0 || index >= listaErrorsize()){return false;}
        listErrors.remove(index);
        return true;
    }
}
