package de.uniluebeck.itm.wiseui.client.testbedselection.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;

import de.uniluebeck.itm.wiseui.api.SNAAServiceAsync;
import de.uniluebeck.itm.wiseui.client.testbedselection.TestbedSelectionPlace;
import de.uniluebeck.itm.wiseui.client.testbedselection.common.UrnPrefixInfo;
import de.uniluebeck.itm.wiseui.client.testbedselection.common.UrnPrefixInfo.State;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ConfigurationSelectedEvent;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ConfigurationSelectedEvent.ConfigurationSelectedHandler;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.LoggedInEvent;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ShowLoginDialogEvent;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ShowLoginDialogEvent.ShowLoginDialogHandler;
import de.uniluebeck.itm.wiseui.client.testbedselection.util.AuthenticationHelper;
import de.uniluebeck.itm.wiseui.client.testbedselection.util.AuthenticationHelper.Callback;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.LoginDialogView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.LoginDialogView.Presenter;
import de.uniluebeck.itm.wiseui.client.util.AuthenticationManager;
import de.uniluebeck.itm.wiseui.shared.TestbedConfiguration;
import de.uniluebeck.itm.wiseui.shared.wiseml.SecretAuthenticationKey;

public class LoginDialogPresenter implements Presenter, ConfigurationSelectedHandler, ShowLoginDialogHandler {    
    
    private final EventBus eventBus;
    
    private final LoginDialogView view;
    
    private final SNAAServiceAsync authenticationService;
    
    private final AuthenticationManager authenticationManager;
    
    private final ListDataProvider<UrnPrefixInfo> dataProvider = new ListDataProvider<UrnPrefixInfo>();
    
    private TestbedConfiguration configuration;
    
    private AuthenticationHelper authenticationHelper;

    @Inject
    public LoginDialogPresenter(final EventBus eventBus, 
            final LoginDialogView view, 
            final SNAAServiceAsync authenticationService,
            final AuthenticationManager authenticationManager) {
        this.eventBus = eventBus;
        this.view = view;
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
        
        dataProvider.addDataDisplay(view.getUrnPrefixList());
        
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
        view.getSubmitEnabled().setEnabled(false);

        final String endpointUrl = configuration.getSnaaEndpointUrl();
        final String username = view.getUsernameText().getText();
        final String password = view.getPasswordText().getText();

        authenticationHelper.authenticate(dataProvider.getList(), endpointUrl, username, password, new Callback() {
            
            private boolean hideAfterComplete = true;
            
            public void onStateChanged(final UrnPrefixInfo info, final State state) {
                dataProvider.refresh();
                if (state.equals(State.FAILED) || state.equals(State.SKIPPED)) {
                    hideAfterComplete = false;
                }
            }
            
            public void onSuccess(SecretAuthenticationKey result) {
            	authenticationManager.addSecretAuthenticationKey(result);
                eventBus.fireEventFromSource(new LoggedInEvent(result), this);
            }
            
            public void onFinish() {
                view.getUsernameEnabled().setEnabled(true);
                view.getPasswordEnabled().setEnabled(true);
                view.getSubmitEnabled().setEnabled(true);
                if (hideAfterComplete) {
                    view.hide();
                }
            }
        });
    }

    public void cancel() {
        //authenticationProvider.cancel();
        view.hide();
    }

    public void onTestbedConfigurationSelected(final ConfigurationSelectedEvent event) {
        configuration = event.getConfiguration();
        dataProvider.getList().clear();
        for (String urnPrefix : configuration.getUrnPrefixList()) {
            dataProvider.getList().add(new UrnPrefixInfo(urnPrefix));
        }
        authenticationHelper = new AuthenticationHelper(authenticationService);
        dataProvider.refresh();
    }

    public void onShowLoginDialog(final ShowLoginDialogEvent event) {
        view.show("Login to " + configuration.getName());
    }
}
