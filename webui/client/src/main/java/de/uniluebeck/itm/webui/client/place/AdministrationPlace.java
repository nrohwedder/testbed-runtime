package de.uniluebeck.itm.webui.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class AdministrationPlace extends Place {

    public static class Tokenizer implements PlaceTokenizer<AdministrationPlace> {

        @Override
        public String getToken(AdministrationPlace place) {
            return "";
        }

        @Override
        public AdministrationPlace getPlace(String token) {
            return new AdministrationPlace();
        }
    }
}
