package de.uniluebeck.itm.wiseui.client.testbedselection.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

import de.uniluebeck.itm.wiseui.client.testbedselection.event.*;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.WisemlLoadedHandler;
import de.uniluebeck.itm.wiseui.client.testbedselection.TestbedSelectionPlace;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.WisemlLoadedEvent;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.DetailView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.DetailView.Presenter;
import de.uniluebeck.itm.wiseui.shared.wiseml.Setup;
import de.uniluebeck.itm.wiseui.shared.wiseml.Wiseml;

public class DetailPresenter implements Presenter, ConfigurationSelectedHandler, WisemlLoadedHandler {

    private final DetailView view;

    private final EventBus eventBus;

    @Inject
    public DetailPresenter(final EventBus eventBus, final DetailView view) {
        this.view = view;
        this.eventBus = eventBus;
        bind();
    }

    private void bind() {
        eventBus.addHandler(ConfigurationSelectedEvent.TYPE, this);
        eventBus.addHandler(WisemlLoadedEvent.TYPE, this);
    }

    public void setPlace(final TestbedSelectionPlace place) {

    }

    public void onTestbedConfigurationSelected(final ConfigurationSelectedEvent event) {
        view.getDescription().setText("Loading details...");
    }

    public void onWisemlLoaded(final WisemlLoadedEvent event) {
        final Wiseml wiseml = event.getWiseml();
        final Setup setup = wiseml.getSetup();
        view.getDescription().setText(setup.getDescription());
        view.setDescriptionCoordinate(setup.getOrigin());
    }
}
