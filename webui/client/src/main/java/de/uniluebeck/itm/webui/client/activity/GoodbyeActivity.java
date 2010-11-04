package de.uniluebeck.itm.webui.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import de.uniluebeck.itm.webui.client.WebUiGinjector;
import de.uniluebeck.itm.webui.client.place.GoodbyePlace;
import de.uniluebeck.itm.webui.client.ui.GoodbyeView;

public class GoodbyeActivity extends AbstractActivity {

    private final WebUiGinjector injector;
    // Name that will be appended to "Good-bye, "
    private String name;

    @Inject
    public GoodbyeActivity(WebUiGinjector injector) {
        this.injector = injector;
    }
    
    public GoodbyeActivity withPlace(GoodbyePlace place) {
    	this.name = place.getGoodbyeName();
    	return this;
    }

    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        GoodbyeView goodbyeView = injector.getGoodbyeView();
        goodbyeView.setName(name);
        containerWidget.setWidget(goodbyeView.asWidget());
    }
}
