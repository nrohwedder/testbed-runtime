package de.uniluebeck.itm.wiseui.client.testbedselection.event;

import com.google.gwt.event.shared.EventHandler;

public interface LoggedInHandler extends EventHandler {

    void onLoggedIn(LoggedInEvent event);
}
