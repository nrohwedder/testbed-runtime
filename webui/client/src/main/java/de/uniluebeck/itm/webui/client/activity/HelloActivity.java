package de.uniluebeck.itm.webui.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import de.uniluebeck.itm.webui.client.WebUiGinjector;
import de.uniluebeck.itm.webui.client.place.HelloPlace;
import de.uniluebeck.itm.webui.client.ui.HelloView;

public class HelloActivity extends AbstractActivity implements HelloView.Presenter {
    // Used to obtain views, eventBus, placeController
    // Alternatively, could be injected via GIN

    private final WebUiGinjector injector;
    // Name that will be appended to "Hello,"
    private String name;

    @Inject
    public HelloActivity(WebUiGinjector injector) {
        this.injector = injector;
    }
    
    public HelloActivity withPlace(HelloPlace place) {
    	this.name = place.getHelloName();
    	return this;
    }

    /**
     * Invoked by the ActivityManager to start a new Activity
     */
    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        HelloView helloView = injector.getHelloView();
        helloView.setName(name);
        helloView.setPresenter(this);
        containerWidget.setWidget(helloView.asWidget());
    }

    /**
     * Ask user before stopping this activity
     */
    @Override
    public String mayStop() {
        return "Please hold on. This activity is stopping.";
    }

    /**
     * Navigate to a new Place in the browser
     */
    public void goTo(Place place) {
    	injector.getPlaceController().goTo(place);
    }
}
