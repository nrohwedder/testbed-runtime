package de.uniluebeck.itm.wiseui.client.activity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import de.uniluebeck.itm.wiseui.client.WiseUiGinjector;
import de.uniluebeck.itm.wiseui.client.administration.AdministrationPlace;
import de.uniluebeck.itm.wiseui.client.experiment.ExperimentationPlace;
import de.uniluebeck.itm.wiseui.client.testbedselection.TestbedSelectionActivity;
import de.uniluebeck.itm.wiseui.client.testbedselection.TestbedSelectionPlace;
import de.uniluebeck.itm.wiseui.client.reservation.ReservationPlace;

public class ContentActivityMapper implements ActivityMapper {

    private WiseUiGinjector injector;

    /**
     * AppActivityMapper associates each Place with its corresponding
     * {@link Activity}
     *
     * @param injector GIN injector to be passed to activities
     */
    @Inject
    public ContentActivityMapper(final WiseUiGinjector injector) {
        super();
        this.injector = injector;
    }

    /**
     * Map each Place to its corresponding Activity.
     */
    public Activity getActivity(final Place place) {
        Activity mappedActivity = null;
        if (place instanceof TestbedSelectionPlace) {
            final TestbedSelectionActivity activity = injector.getLoginActivity();
            activity.setPlace((TestbedSelectionPlace) place);
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
