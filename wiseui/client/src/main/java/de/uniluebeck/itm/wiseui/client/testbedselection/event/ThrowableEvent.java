package de.uniluebeck.itm.wiseui.client.testbedselection.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import de.uniluebeck.itm.wiseui.client.testbedselection.event.ThrowableEvent.ThrowableHandler;

public class ThrowableEvent extends GwtEvent<ThrowableHandler> {

	public interface ThrowableHandler extends EventHandler {
		void onThrowable(ThrowableEvent event);
	}
	
	public static final Type<ThrowableHandler> TYPE = new Type<ThrowableHandler>();
	
	private final Throwable throwable;
	
	public ThrowableEvent(Throwable throwable) {
		this.throwable = throwable;
	}
	
	protected void dispatch(ThrowableHandler handler) {
		handler.onThrowable(this);
	}
	
	@Override
	public Type<ThrowableHandler> getAssociatedType() {
		return TYPE;
	}
	
	public Throwable getThrowable() {
		return throwable;
	}
}
