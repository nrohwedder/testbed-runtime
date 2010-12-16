package de.uniluebeck.itm.wiseui.client.event;

import com.google.gwt.event.shared.GwtEvent;

import de.uniluebeck.itm.wiseui.shared.wiseml.Wiseml;

public class WisemlLoadedEvent extends GwtEvent<WisemlLoadedHandler> {

    public static final Type<WisemlLoadedHandler> TYPE = new Type<WisemlLoadedHandler>();

    private final Wiseml wiseml;

    public WisemlLoadedEvent(final Wiseml wiseml) {
        this.wiseml = wiseml;
    }

    @Override
    public Type<WisemlLoadedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final WisemlLoadedHandler handler) {
        handler.onWisemlLoaded(this);
    }

    public Wiseml getWiseml() {
        return wiseml;
    }
}
