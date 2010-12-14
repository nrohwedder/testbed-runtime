package de.uniluebeck.itm.webui.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ConfigurationSelectedHandler extends EventHandler {

    void onTestbedConfigurationSelected(ConfigurationSelectedEvent event);
}
