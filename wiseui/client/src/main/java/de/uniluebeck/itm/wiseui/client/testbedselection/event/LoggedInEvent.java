package de.uniluebeck.itm.wiseui.client.testbedselection.event;

import com.google.gwt.event.shared.GwtEvent;
import de.uniluebeck.itm.wiseui.shared.wiseml.SecretAuthenticationKey;

public class LoggedInEvent extends GwtEvent<LoggedInHandler> {

    public static final Type<LoggedInHandler> TYPE = new Type<LoggedInHandler>();

    private final SecretAuthenticationKey secretAuthenticationKey;

    public LoggedInEvent(final SecretAuthenticationKey secretAuthenticationKey) {
        this.secretAuthenticationKey = secretAuthenticationKey;
    }

    @Override
    public Type<LoggedInHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final LoggedInHandler handler) {
        handler.onLoggedIn(this);
    }

    public SecretAuthenticationKey getSecretAuthenticationKey() {
        return secretAuthenticationKey;
    }
}
