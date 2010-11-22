package de.uniluebeck.itm.webui.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import de.uniluebeck.itm.webui.client.WebUiGinjector;
import de.uniluebeck.itm.webui.client.place.*;
import de.uniluebeck.itm.webui.client.ui.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends AbstractActivity implements NavigationView.Presenter {

    private class Entry {
        private Place place;

        private String name;

        public Entry(String name, Place place) {
            this.name = name;
            this.place = place;
        }

        public String getName() {
            return name;
        }

        public Place getPlace() {
            return place;
        }
    }

    private WebUiGinjector injector;

    private NavigationView navigationView;

    private List<Entry> navigation = new ArrayList<Entry>();

    private Place place;

    @Inject
    public NavigationActivity(WebUiGinjector injector) {
        this.injector = injector;

        navigation.add(new Entry("Login", new LoginPlace(null)));
        navigation.add(new Entry("Reservation", new ReservationPlace()));
        navigation.add(new Entry("Experimentation", new ExperimentationPlace()));
        navigation.add(new Entry("Administration", new AdministrationPlace()));
        navigation.add(new Entry("WiseML Native", new WiseMLNativePlace()));
    }

    public void start(AcceptsOneWidget container, EventBus eventBus) {
        GWT.log("Start navigation activity");
        navigationView = injector.getNavigationView();
        navigationView.setPresenter(this);

        initTabs();
        updateSelection();

        container.setWidget(navigationView);
    }

    private void initTabs() {
        for (Entry entry : navigation) {
            navigationView.add(entry.getName());
        }
    }

    public void updateSelection() {
        if (place == null) {
            navigationView.select(0);
        }
        int i = 0;
        for (Entry entry : navigation) {
            if (place != null && place.getClass().equals(entry.getPlace().getClass())) {
                navigationView.select(i);
            }
            i++;
        }
    }

    public void selected(Integer index) {
        Entry entry = navigation.get(index);
        GWT.log("Go to place: " + entry.getName());
        injector.getPlaceController().goTo(entry.getPlace());
    }

    public void setPlace(Place place) {
        this.place = place;
        if (navigationView != null) {
            updateSelection();
        }
    }
}
