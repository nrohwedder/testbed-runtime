package de.uniluebeck.itm.wiseui.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoginPlace extends Place {

    private Integer selection;

    public LoginPlace() {
        this.selection = null;
    }

    public LoginPlace(final Integer selection) {
        this.selection = selection;
    }

    public void setSelection(final Integer selection) {
        this.selection = selection;
    }

    public Integer getSelection() {
        return selection;
    }

    public static class Tokenizer implements PlaceTokenizer<LoginPlace> {

        public String getToken(final LoginPlace place) {
            return place.getSelection() != null ? String.valueOf(place.getSelection()) : "";
        }

        public LoginPlace getPlace(final String token) {
            return new LoginPlace("".equals(token) ? null
                    : Integer.parseInt(token));
        }
    }
}
