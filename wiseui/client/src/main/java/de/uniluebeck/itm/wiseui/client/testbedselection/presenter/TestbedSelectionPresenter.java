package de.uniluebeck.itm.wiseui.client.testbedselection.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import de.uniluebeck.itm.wiseui.client.testbedselection.TestbedSelectionPlace;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ConfigurationSelectedEvent;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ConfigurationSelectedHandler;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ShowLoginDialogEvent;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.WisemlLoadedEvent;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.WisemlLoadedHandler;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.TestbedSelectionView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.TestbedSelectionView.Presenter;
import de.uniluebeck.itm.wiseui.shared.TestbedConfiguration;

public class TestbedSelectionPresenter implements Presenter, ConfigurationSelectedHandler, WisemlLoadedHandler {

    private final EventBus eventBus;

    private final TestbedSelectionView view;

    private TestbedConfiguration configuration;

    @Inject
    public TestbedSelectionPresenter(final EventBus eventBus, final TestbedSelectionView view) {
        this.eventBus = eventBus;
        this.view = view;
        view.getLoginEnabled().setEnabled(false);
        view.getReloadEnabled().setEnabled(false);
        bind();
    }

    private void bind() {
        eventBus.addHandler(WisemlLoadedEvent.TYPE, this);
        eventBus.addHandler(ConfigurationSelectedEvent.TYPE, this);
    }

    public void reload() {
        view.getReloadEnabled().setEnabled(false);
        eventBus.fireEvent(new ConfigurationSelectedEvent(configuration));
    }

    public void showLoginDialog() {
        eventBus.fireEventFromSource(new ShowLoginDialogEvent(), this);
    }

    public void setPlace(final TestbedSelectionPlace place) {

    }

    public void onWisemlLoaded(final WisemlLoadedEvent event) {
        view.getReloadEnabled().setEnabled(true);
    }

    public void onTestbedConfigurationSelected(final ConfigurationSelectedEvent event) {
        configuration = event.getConfiguration();
        view.getLoginEnabled().setEnabled(true);
    }

}
