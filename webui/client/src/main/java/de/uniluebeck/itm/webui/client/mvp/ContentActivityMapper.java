package de.uniluebeck.itm.webui.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import de.uniluebeck.itm.webui.client.WebUiGinjector;
import de.uniluebeck.itm.webui.client.login.LoginActivity;
import de.uniluebeck.itm.webui.client.place.AdministrationPlace;
import de.uniluebeck.itm.webui.client.place.ExperimentationPlace;
import de.uniluebeck.itm.webui.client.place.LoginPlace;
import de.uniluebeck.itm.webui.client.place.ReservationPlace;
import de.uniluebeck.itm.webui.client.place.WiseMLNativePlace;

public class ContentActivityMapper implements ActivityMapper {

    private WebUiGinjector injector;

    /**
     * AppActivityMapper associates each Place with its corresponding
     * {@link Activity}
     * 
     * @param injector
     *            GIN injector to be passed to activities
     */
    @Inject
    public ContentActivityMapper(final WebUiGinjector injector) {
        super();
        this.injector = injector;
    }

    /**
     * Map each Place to its corresponding Activity.
     */
    public Activity getActivity(final Place place) {
        Activity mappedActivity = null;
        if (place instanceof LoginPlace) {
            final LoginActivity activity = injector.getLoginActivity();
            activity.setPlace((LoginPlace) place);
            mappedActivity = activity;
        }
        if (place instanceof ReservationPlace) {
            mappedActivity = injector.getReservationActivity();
        }
        if (place instanceof ExperimentationPlace) {
            mappedActivity = injector.getExperimentationActivity();
        }
        if (place instanceof AdministrationPlace) {
            mappedActivity = injector.getAdministrationActivity();
        }
        if (place instanceof WiseMLNativePlace) {
            mappedActivity = injector.getWiseMLNativeActivity();
        }
        return mappedActivity;
    }
}
