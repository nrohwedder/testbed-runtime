package de.uniluebeck.itm.webui.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;

import de.uniluebeck.itm.webui.client.WebUiGinjector;
import de.uniluebeck.itm.webui.client.activity.GoodbyeActivity;
import de.uniluebeck.itm.webui.client.activity.HelloActivity;
import de.uniluebeck.itm.webui.client.place.GoodbyePlace;
import de.uniluebeck.itm.webui.client.place.HelloPlace;

public class AppActivityMapper implements ActivityMapper {
	
    private WebUiGinjector injector;

    /**
     * AppActivityMapper associates each Place with its corresponding
     * {@link Activity}
     *
     * @param clientFactory
     *            Factory to be passed to activities
     */
    @Inject
    public AppActivityMapper(WebUiGinjector injector) {
        super();
        this.injector = injector;
    }

    /**
     * Map each Place to its corresponding Activity. This would be a great use
     * for GIN.
     */
    @Override
    public Activity getActivity(Place place) {
        // This is begging for GIN
        if (place instanceof HelloPlace) {
            return new HelloActivity((HelloPlace) place, injector);
        } else if (place instanceof GoodbyePlace) {
            return new GoodbyeActivity((GoodbyePlace) place, injector);
        }

        return null;
    }
}
