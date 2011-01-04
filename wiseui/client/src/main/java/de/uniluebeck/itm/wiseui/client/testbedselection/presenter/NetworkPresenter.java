package de.uniluebeck.itm.wiseui.client.testbedselection.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

import de.uniluebeck.itm.wiseui.client.testbedselection.TestbedSelectionPlace;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.WisemlLoadedEvent;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.WisemlLoadedEvent.WisemlLoadedHandler;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.NetworkView;
import de.uniluebeck.itm.wiseui.shared.wiseml.Setup;
import de.uniluebeck.itm.wiseui.shared.wiseml.Wiseml;

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

    public void setPlace(final TestbedSelectionPlace place) {

    }

    public void onWisemlLoaded(final WisemlLoadedEvent event) {
        final Wiseml wiseml = event.getWiseml();
        final Setup setup = wiseml.getSetup();
        view.setNodes(setup.getNode());
    }
}
