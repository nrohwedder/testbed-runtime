package de.uniluebeck.itm.webui.client.presenter.login;

import java.util.Iterator;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import de.uniluebeck.itm.webui.api.SNAAServiceAsync;
import de.uniluebeck.itm.webui.client.event.LoggedInEvent;
import de.uniluebeck.itm.webui.client.presenter.login.LoginDialogPresenter.AuthenticationState;
import de.uniluebeck.itm.webui.client.presenter.login.LoginDialogPresenter.UrnPrefixInfo;
import de.uniluebeck.itm.webui.shared.wiseml.SecretAuthenticationKey;

public class AuthenticationHelper implements AsyncCallback<SecretAuthenticationKey> {
    
    public interface Callback {
        void onStateChanged(UrnPrefixInfo info, AuthenticationState state);
        
        void onComplete();
    }
    
    private final SNAAServiceAsync authenticationService;
    
    private final EventBus eventBus;
    
    private String endpointUrl;
    
    private String username;
    
    private String password;
    
    private Iterator<UrnPrefixInfo> iterator;
    
    private Callback callback;
    
    private UrnPrefixInfo current;
    
    private Request request;
    
    private boolean canceled = false;
    
    @Inject
    public AuthenticationHelper(final EventBus eventBus, final SNAAServiceAsync authenticationService) {
        this.eventBus = eventBus;
        this.authenticationService = authenticationService;
    }
    
    public void onSuccess(final SecretAuthenticationKey result) {
        current.setState(AuthenticationState.SUCCESS);
        callback.onStateChanged(current, AuthenticationState.SUCCESS);
        eventBus.fireEventFromSource(new LoggedInEvent(result), this);
        proceedNext();
    }
    
    public void onFailure(final Throwable caught) {
        current.setState(AuthenticationState.FAILED);
        callback.onStateChanged(current, AuthenticationState.FAILED);
        proceedNext();
    }
    
    private void proceedNext() {
        if (iterator.hasNext() && !canceled) {
            current = iterator.next();
            if (current.isChecked()) {
                current.setState(AuthenticationState.AUTHENTICATE);
                callback.onStateChanged(current, AuthenticationState.AUTHENTICATE);
                request = authenticationService.authenticate(endpointUrl, current.getUrnPrefix(), username, password, this);
            } else {
                current.setState(AuthenticationState.SKIPPED);
                callback.onStateChanged(current, AuthenticationState.SKIPPED);
                proceedNext();
            }
        } else {
            if (canceled) {
                current.setState(AuthenticationState.CANCELED);
                callback.onStateChanged(current, AuthenticationState.CANCELED);
            }
            callback.onComplete();
        }
    }
    
    public void cancel() {
        if (request != null && request.isPending()) {
            request.cancel();
            current.setState(AuthenticationState.CANCELED);
            callback.onStateChanged(current, AuthenticationState.CANCELED);
            proceedNext();
        }
        canceled = true;
    }
    
    public void authenticate(final Iterator<UrnPrefixInfo> iterator, final String endpointUrl, final String username, final String password, final Callback callback) {
        this.iterator = iterator;
        this.endpointUrl = endpointUrl;
        this.username = username;
        this.password = password;
        this.callback = callback;
        proceedNext();
    }
}
