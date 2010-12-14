package de.uniluebeck.itm.webui.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface LoggedInHandler extends EventHandler {

    void onLoggedIn(LoggedInEvent event);
}
