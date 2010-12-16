package de.uniluebeck.itm.wiseui.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class WiseMLNativePlace extends Place {

    public static class Tokenizer implements PlaceTokenizer<WiseMLNativePlace> {

        public String getToken(final WiseMLNativePlace place) {
            return "";
        }

        public WiseMLNativePlace getPlace(final String token) {
            return new WiseMLNativePlace();
        }
    }
}
