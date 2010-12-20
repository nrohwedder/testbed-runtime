package de.uniluebeck.itm.wiseui.client.testbedselection.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;

import de.uniluebeck.itm.wiseui.api.SNAAServiceAsync;
import de.uniluebeck.itm.wiseui.client.testbedselection.TestbedSelectionPlace;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ConfigurationSelectedEvent;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ConfigurationSelectedHandler;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ShowLoginDialogEvent;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ShowLoginDialogHandler;
import de.uniluebeck.itm.wiseui.client.testbedselection.util.AuthenticationHelper;
import de.uniluebeck.itm.wiseui.client.testbedselection.util.AuthenticationHelper.Callback;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.LoginDialogView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.LoginDialogView.Presenter;
import de.uniluebeck.itm.wiseui.shared.TestbedConfiguration;

public class LoginDialogPresenter implements Presenter, ConfigurationSelectedHandler, ShowLoginDialogHandler {

    public enum AuthenticationState {
        NOT_AUTHENTICATED("Not authenticated"),
        AUTHENTICATE("Authenticate..."),
        SUCCESS("Successful"), 
        FAILED("Failed due an error"),
        CANCELED("Canceled"), 
        SKIPPED("Skipped");
        
        private final String value;
        
        private AuthenticationState(final String value) {
            this.value = value;
        }
        
        @Override
        public String toString() {
            return value;
        }
    }
    
    public class UrnPrefixInfo {
        
        private final String urnPrefix;
        
        private AuthenticationState state;
        
        private boolean checked;
        
        public UrnPrefixInfo(final String urnPrefix) {
            this.urnPrefix = urnPrefix;
            state = AuthenticationState.NOT_AUTHENTICATED;
            checked = true;
        }
        
        public String getUrnPrefix() {
            return urnPrefix;
        }
        
        public AuthenticationState getState() {
            return state;
        }
        
        public void setState(final AuthenticationState state) {
            this.state = state;
        }
        
        public boolean isChecked() {
            return checked;
        }
        
        public void setChecked(final boolean checked) {
            this.checked = checked;
        }
    }
    
    private final EventBus eventBus;
    
    private final LoginDialogView view;
    
    private final SNAAServiceAsync authenticationService;
    
    private final ListDataProvider<UrnPrefixInfo> dataProvider = new ListDataProvider<LoginDialogPresenter.UrnPrefixInfo>();
    
    private TestbedConfiguration configuration;
    
    private AuthenticationHelper authenticationHelper;

    @Inject
    public LoginDialogPresenter(final EventBus eventBus, 
            final LoginDialogView view, 
            final SNAAServiceAsync authenticationService) {
        this.eventBus = eventBus;
        this.view = view;
        this.authenticationService = authenticationService;
        
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

        authenticationHelper.authenticate(dataProvider.getList().iterator(), endpointUrl, username, password, new Callback() {
            
            private boolean hideAfterComplete = true;
            
            public void onStateChanged(final UrnPrefixInfo info, final AuthenticationState state) {
                dataProvider.refresh();
                if (state.equals(AuthenticationState.FAILED) || state.equals(AuthenticationState.SKIPPED)) {
                    hideAfterComplete = false;
                }
            }
            
            public void onComplete() {
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
        authenticationHelper = new AuthenticationHelper(eventBus, authenticationService);
        dataProvider.refresh();
    }

    public void onShowLoginDialog(final ShowLoginDialogEvent event) {
        view.show("Login to " + configuration.getName());
    }
}
