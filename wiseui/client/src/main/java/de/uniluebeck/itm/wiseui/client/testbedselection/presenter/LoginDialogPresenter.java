package de.uniluebeck.itm.wiseui.client.testbedselection.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import de.uniluebeck.itm.wiseui.api.SNAAServiceAsync;
import de.uniluebeck.itm.wiseui.client.testbedselection.TestbedSelectionPlace;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ConfigurationSelectedEvent;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ConfigurationSelectedHandler;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.LoggedInEvent;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ShowLoginDialogEvent;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ShowLoginDialogHandler;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.LoginDialogView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.LoginDialogView.Presenter;
import de.uniluebeck.itm.wiseui.shared.TestbedConfiguration;
import de.uniluebeck.itm.wiseui.shared.wiseml.SecretAuthenticationKey;

import java.util.Iterator;

public class LoginDialogPresenter implements Presenter, ConfigurationSelectedHandler, ShowLoginDialogHandler {

    private final EventBus eventBus;

    private final LoginDialogView view;

    private final SNAAServiceAsync authenticationService;

    private TestbedConfiguration configuration;

    @Inject
    public LoginDialogPresenter(final EventBus eventBus,
                                final LoginDialogView view,
                                final SNAAServiceAsync authenticationService) {
        this.eventBus = eventBus;
        this.view = view;
        this.authenticationService = authenticationService;

        bind();
    }

    private void bind() {
        eventBus.addHandler(ConfigurationSelectedEvent.TYPE, this);
        eventBus.addHandler(ShowLoginDialogEvent.TYPE, this);
    }

    public void setPlace(final TestbedSelectionPlace place) {

    }

    public void submit() {
        view.getUsernameEnabled().setEnabled(false);
        view.getPasswordEnabled().setEnabled(false);

        final String endpointUrl = configuration.getSnaaEndpointUrl();
        final String username = view.getUsernameText().getText();
        final String password = view.getPasswordText().getText();

        final Iterator<String> iterator = configuration.getUrnPrefixList().iterator();

        final AsyncCallback<SecretAuthenticationKey> callback = new AsyncCallback<SecretAuthenticationKey>() {

            private String urn;
            private boolean error = false;

            public void onSuccess(final SecretAuthenticationKey result) {
                if (iterator.hasNext()) {
                    urn = iterator.next();
                    authenticationService.authenticate(endpointUrl, urn, username, password, this);
                } else {
                    if (!error) {
                        view.hide();
                    }
                    view.getUsernameEnabled().setEnabled(true);
                    view.getPasswordEnabled().setEnabled(true);
                    eventBus.fireEventFromSource(new LoggedInEvent(result), LoginDialogPresenter.this);
                }
            }

            public void onFailure(final Throwable throwable) {
                view.clearErrors();
                error = true;
                view.addError(urn + " " + throwable.getMessage());
                onSuccess(null); // ??
            }
        };
        callback.onSuccess(null); // ??
    }

    public void cancel() {
        view.hide();
    }

    public void onTestbedConfigurationSelected(final ConfigurationSelectedEvent event) {
        configuration = event.getConfiguration();
    }

    public void onShowLoginDialog(final ShowLoginDialogEvent event) {
        view.show("Login to " + configuration.getName());
    }
}
