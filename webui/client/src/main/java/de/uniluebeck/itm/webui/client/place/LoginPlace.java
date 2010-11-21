package de.uniluebeck.itm.webui.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoginPlace extends Place {

	private Integer selection;
	
	public LoginPlace() {
		this.selection = null;
	}
	
    public LoginPlace(Integer selection) {
    	this.selection = selection;
    }
    
    public void setSelection(Integer selection) {
    	this.selection = selection;
    }
    
    public Integer getSelection() {
    	return selection;
    }

    public static class Tokenizer implements PlaceTokenizer<LoginPlace> {

        @Override
        public String getToken(LoginPlace place) {
            return place.getSelection() != null ? String.valueOf(place.getSelection()) : "";
        }

        @Override
        public LoginPlace getPlace(String token) {
            return new LoginPlace(token.equals("") ? null : Integer.parseInt(token));
        }
    }
}
