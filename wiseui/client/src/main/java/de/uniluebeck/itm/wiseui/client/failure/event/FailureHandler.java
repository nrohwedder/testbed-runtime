package de.uniluebeck.itm.wiseui.client.failure.event;

import com.google.gwt.event.shared.EventHandler;

public interface FailureHandler extends EventHandler {

    void handleFailure(FailureEvent event);
}
