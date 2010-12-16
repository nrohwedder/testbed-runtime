package de.uniluebeck.itm.wiseui.client.testbedselection.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowLoginDialogEvent extends GwtEvent<ShowLoginDialogHandler> {

    public static final Type<ShowLoginDialogHandler> TYPE = new Type<ShowLoginDialogHandler>();

    @Override
    public GwtEvent.Type<ShowLoginDialogHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final ShowLoginDialogHandler handler) {
        handler.onShowLoginDialog(this);
    }
}
