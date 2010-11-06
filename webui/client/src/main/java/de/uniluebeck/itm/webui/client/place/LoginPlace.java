package de.uniluebeck.itm.webui.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoginPlace extends Place {

    private String helloName;

    public LoginPlace(String token) {
        this.helloName = token;
    }

    public String getHelloName() {
        return helloName;
    }

    public static class Tokenizer implements PlaceTokenizer<LoginPlace> {

        @Override
        public String getToken(LoginPlace place) {
            return place.getHelloName();
        }

        @Override
        public LoginPlace getPlace(String token) {
            return new LoginPlace(token);
        }
    }
}
