package de.uniluebeck.itm.webui.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import de.uniluebeck.itm.webui.client.place.GoodbyePlace;
import de.uniluebeck.itm.webui.client.ui.GoodbyeView;
import de.uniluebeck.itm.webui.client.ClientFactory;

public class GoodbyeActivity extends AbstractActivity {

    private ClientFactory clientFactory;
    // Name that will be appended to "Good-bye, "
    private String name;

    public GoodbyeActivity(GoodbyePlace place, ClientFactory clientFactory) {
        this.name = place.getGoodbyeName();
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        GoodbyeView goodbyeView = clientFactory.getGoodbyeView();
        goodbyeView.setName(name);
        containerWidget.setWidget(goodbyeView.asWidget());
    }
}
