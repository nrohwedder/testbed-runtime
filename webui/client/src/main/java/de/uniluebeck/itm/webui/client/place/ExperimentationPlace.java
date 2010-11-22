package de.uniluebeck.itm.webui.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ExperimentationPlace extends Place {

    public static class Tokenizer implements PlaceTokenizer<ExperimentationPlace> {

        public String getToken(ExperimentationPlace place) {
            return "";
        }

        public ExperimentationPlace getPlace(String token) {
            return new ExperimentationPlace();
        }
    }
}
