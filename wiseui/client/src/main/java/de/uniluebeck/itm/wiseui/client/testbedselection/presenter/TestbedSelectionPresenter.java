package de.uniluebeck.itm.wiseui.client.testbedselection.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

import de.uniluebeck.itm.wiseui.client.testbedselection.TestbedSelectionPlace;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ConfigurationSelectedEvent;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ConfigurationSelectedEvent.ConfigurationSelectedHandler;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ShowLoginDialogEvent;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ThrowableEvent;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.WisemlLoadedEvent;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ThrowableEvent.ThrowableHandler;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.WisemlLoadedEvent.WisemlLoadedHandler;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.TestbedSelectionView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.TestbedSelectionView.Presenter;
import de.uniluebeck.itm.wiseui.client.util.MessageBox;
import de.uniluebeck.itm.wiseui.shared.TestbedConfiguration;
import de.uniluebeck.itm.wiseui.shared.exception.WisemlException;

public class TestbedSelectionPresenter implements Presenter, ConfigurationSelectedHandler, WisemlLoadedHandler, ThrowableHandler {

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
        eventBus.addHandler(ThrowableEvent.TYPE, this);
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

    public void onThrowable(final ThrowableEvent event) {
        if (event.getThrowable() instanceof WisemlException) {
            final String title = "Unavailable Testbed " + configuration.getName();
            final String message = "The Testbed " 
                    + configuration.getName() 
                    + " is not available.\n" 
                    + event.getThrowable().getMessage();
            MessageBox.error(title, message, event.getThrowable(), null);
        }
    }
}
