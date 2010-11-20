package de.uniluebeck.itm.webui.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class WiseMLNativePlace extends Place {

    public static class Tokenizer implements PlaceTokenizer<WiseMLNativePlace> {

        @Override
        public String getToken(WiseMLNativePlace place) {
            return "";
        }

        @Override
        public WiseMLNativePlace getPlace(String token) {
            return new WiseMLNativePlace();
        }
    }
}
