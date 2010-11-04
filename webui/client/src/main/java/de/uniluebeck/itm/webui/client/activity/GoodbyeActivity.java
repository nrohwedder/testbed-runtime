package de.uniluebeck.itm.webui.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.uniluebeck.itm.webui.client.WebUiGinjector;
import de.uniluebeck.itm.webui.client.place.GoodbyePlace;
import de.uniluebeck.itm.webui.client.ui.GoodbyeView;

public class GoodbyeActivity extends AbstractActivity {

    private WebUiGinjector injector;
    // Name that will be appended to "Good-bye, "
    private String name;

    public GoodbyeActivity(GoodbyePlace place, WebUiGinjector injector) {
        this.name = place.getGoodbyeName();
        this.injector = injector;
    }

    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        GoodbyeView goodbyeView = injector.getGoodbyeView();
        goodbyeView.setName(name);
        containerWidget.setWidget(goodbyeView.asWidget());
    }
}
