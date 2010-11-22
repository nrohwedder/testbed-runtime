package de.uniluebeck.itm.webui.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoginPlace extends Place {

    private String name;

    public LoginPlace(String token) {
        this.name = token;
    }

    public String getLoginName() {
        return name;
    }

    public static class Tokenizer implements PlaceTokenizer<LoginPlace> {

        public String getToken(LoginPlace place) {
            return place.getLoginName();
        }

        public LoginPlace getPlace(String token) {
            return new LoginPlace(token);
        }
    }
}
