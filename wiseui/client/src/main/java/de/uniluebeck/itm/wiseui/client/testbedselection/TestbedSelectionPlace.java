package de.uniluebeck.itm.wiseui.client.testbedselection;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class TestbedSelectionPlace extends Place {

    private Integer selection;

    public TestbedSelectionPlace() {
        this.selection = null;
    }

    public TestbedSelectionPlace(final Integer selection) {
        this.selection = selection;
    }

    public void setSelection(final Integer selection) {
        this.selection = selection;
    }

    public Integer getSelection() {
        return selection;
    }

    public static class Tokenizer implements PlaceTokenizer<TestbedSelectionPlace> {

        public String getToken(final TestbedSelectionPlace place) {
            return place.getSelection() != null ? String.valueOf(place.getSelection()) : "";
        }

        public TestbedSelectionPlace getPlace(final String token) {
            return new TestbedSelectionPlace("".equals(token) ? null
                    : Integer.parseInt(token));
        }
    }
}
