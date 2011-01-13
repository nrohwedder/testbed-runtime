package de.uniluebeck.itm.wiseui.client.failure.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class FailureEvent extends GwtEvent<FailureEvent.FailureHandler> {

    public interface FailureHandler extends EventHandler {

        void handleFailure(FailureEvent event);
    }
    public static final Type<FailureHandler> TYPE = new Type<FailureHandler>();
    private String message;
    private String stacktrace;
    private Throwable cause;

    public FailureEvent(final String message, final String stacktrace) {
        this.message = message;
        this.stacktrace = stacktrace;
    }

    public FailureEvent(final String message, final String stacktrace, final Throwable cause) {
        this.message = message;
        this.stacktrace = stacktrace;
        this.cause = cause;
    }

    @Override
    public GwtEvent.Type<FailureHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final FailureHandler handler) {
        handler.handleFailure(this);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(final String stacktrace) {
        this.stacktrace = stacktrace;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(final Throwable cause) {
        this.cause = cause;
    }
}
