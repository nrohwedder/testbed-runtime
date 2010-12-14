package de.uniluebeck.itm.webui.client.presenter.login;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

import de.uniluebeck.itm.webui.client.event.ConfigurationSelectedEvent;
import de.uniluebeck.itm.webui.client.event.ConfigurationSelectedHandler;
import de.uniluebeck.itm.webui.client.event.ShowLoginDialogEvent;
import de.uniluebeck.itm.webui.client.event.WisemlLoadedEvent;
import de.uniluebeck.itm.webui.client.event.WisemlLoadedHandler;
import de.uniluebeck.itm.webui.client.place.LoginPlace;
import de.uniluebeck.itm.webui.client.ui.login.LoginView;
import de.uniluebeck.itm.webui.client.ui.login.LoginView.Presenter;
import de.uniluebeck.itm.webui.shared.TestbedConfiguration;

public class LoginPresenter implements Presenter, ConfigurationSelectedHandler, WisemlLoadedHandler {

    private final EventBus eventBus;
    
    private final LoginView view;
    
    private TestbedConfiguration configuration;
    
    @Inject
    public LoginPresenter(final EventBus eventBus, final LoginView view) {
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

    public void setPlace(final LoginPlace place) {
        
    }

    public void onWisemlLoaded(final WisemlLoadedEvent event) {
        view.getReloadEnabled().setEnabled(true);
    }

    public void onTestbedConfigurationSelected(final ConfigurationSelectedEvent event) {
        configuration = event.getConfiguration();
        view.getLoginEnabled().setEnabled(true);
    }

}
