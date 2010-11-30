package de.uniluebeck.itm.webui.client.place;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

import de.uniluebeck.itm.webui.client.mvp.WebUiPlaceHistoryMapper;

public class WebUiPlace extends Place {

    private Map<String, Place> places = new TreeMap<String, Place>();
    private String current;

    public WebUiPlace() {
    }

    private WebUiPlace(final Map<String, Place> places) {
        this.places = places;
    }

    public Collection<Place> getPlaces() {
        return places.values();
    }

    public Place get(final Class<? extends Place> place) {
        return places.get(place.getName());
    }

    public Place getCurrent() {
        return places.get(current);
    }

    public void setCurrent(final Place place) {
        current = place.getClass().getName();
        places.put(current, place);
    }

    public WebUiPlace update(final Place place) {
        places.put(place.getClass().getName(), place);
        return new WebUiPlace(places);
    }

    public static class Tokenizer implements PlaceTokenizer<WebUiPlace> {

        private static final String SEPARATOR = "&";
        private final WebUiPlaceHistoryMapper mapper = GWT.create(WebUiPlaceHistoryMapper.class);

        private String[] removeLast(final String[] array) {
            final String[] result = new String[array.length - 1];
            System.arraycopy(array, 0, result, 0, array.length - 1);
            return result;
        }

        public String getToken(final WebUiPlace place) {
            final StringBuilder buffer = new StringBuilder();
            for (Place entry : place.getPlaces()) {
                buffer.append(mapper.getToken(entry)).append(SEPARATOR);
            }
            final String current = place.getCurrent().getClass().getName();
            buffer.append(current);
            return buffer.toString();
        }

        public WebUiPlace getPlace(final String token) {
            final WebUiPlace webUiPlace = new WebUiPlace();
            final String[] tokens = token.split(SEPARATOR);
            if (tokens.length >= 2) {
                final String[] entries = removeLast(tokens);
                final String current = tokens[tokens.length - 1];
                for (String entry : entries) {
                    final Place place = mapper.getPlace(entry);
                    webUiPlace.update(place);
                    if (current.equals(place.getClass().getName())) {
                        webUiPlace.setCurrent(place);
                    }
                }
            }
            return webUiPlace;
        }
    }
}
