package de.uniluebeck.itm.webui.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ReservationPlace extends Place {
	
    public ReservationPlace() {
    }

    public static class Tokenizer implements PlaceTokenizer<ReservationPlace> {

        public String getToken(ReservationPlace place) {
            return "";
        }

        public ReservationPlace getPlace(String token) {
            return new ReservationPlace();
        }
    }
}
