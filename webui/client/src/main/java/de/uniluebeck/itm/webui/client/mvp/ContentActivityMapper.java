package de.uniluebeck.itm.webui.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;

import de.uniluebeck.itm.webui.client.WebUiGinjector;
import de.uniluebeck.itm.webui.client.place.LoginPlace;

public class ContentActivityMapper implements ActivityMapper {
	
    private WebUiGinjector injector;

    /**
     * AppActivityMapper associates each Place with its corresponding
     * {@link Activity}
     *
     * @param injector GIN injector for dependency injection
     */
    @Inject
    public ContentActivityMapper(WebUiGinjector injector) {
        super();
        this.injector = injector;
    }

    /**
     * Map each Place to its corresponding Activity.
     */
    public Activity getActivity(Place place) {
    	Activity activity = null;
        if (place instanceof LoginPlace) {
        	activity = injector.getLoginActivity();
        }
        return activity;
    }
}
