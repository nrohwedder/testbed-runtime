package de.uniluebeck.itm.webui.client.presenter.login;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

import de.uniluebeck.itm.webui.client.event.WisemlLoadedEvent;
import de.uniluebeck.itm.webui.client.event.WisemlLoadedHandler;
import de.uniluebeck.itm.webui.client.place.LoginPlace;
import de.uniluebeck.itm.webui.client.ui.login.NetworkView;
import de.uniluebeck.itm.webui.shared.wiseml.Setup;
import de.uniluebeck.itm.webui.shared.wiseml.Wiseml;

public class NetworkPresenter implements NetworkView.Presenter, WisemlLoadedHandler {

    private final NetworkView view;
    
    private final EventBus eventBus;
    
    @Inject
    public NetworkPresenter(final EventBus eventBus, final NetworkView view) {
        this.view = view;
        this.eventBus = eventBus;
        bind();
    }
    
    private void bind() {
        eventBus.addHandler(WisemlLoadedEvent.TYPE, this);
    }
    
    public void setPlace(final LoginPlace place) {

    }

    public void onWisemlLoaded(final WisemlLoadedEvent event) {
        final Wiseml wiseml = event.getWiseml();
        final Setup setup = wiseml.getSetup();
        view.setNodes(setup.getNode());
    }
}
