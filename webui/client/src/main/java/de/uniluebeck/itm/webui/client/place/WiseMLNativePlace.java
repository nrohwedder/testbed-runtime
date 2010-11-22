package de.uniluebeck.itm.webui.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class WiseMLNativePlace extends Place {

    public static class Tokenizer implements PlaceTokenizer<WiseMLNativePlace> {

        public String getToken(WiseMLNativePlace place) {
            return "";
        }

        public WiseMLNativePlace getPlace(String token) {
            return new WiseMLNativePlace();
        }
    }
}
