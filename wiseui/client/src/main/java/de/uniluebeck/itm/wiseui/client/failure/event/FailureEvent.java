package de.uniluebeck.itm.wiseui.client.failure.event;

import com.google.gwt.event.shared.GwtEvent;

public class FailureEvent extends GwtEvent<FailureHandler> {

    public static final Type<FailureHandler> TYPE = new Type<FailureHandler>();
    private String message;
    private String stacktrace;
    Throwable cause;

    public FailureEvent(String message, String stacktrace) {
        this.message = message;
        this.stacktrace = stacktrace;
    }

    public FailureEvent(String message, String stacktrace, Throwable cause) {
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

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }
}
