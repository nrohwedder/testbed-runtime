package de.uniluebeck.itm.wiseui.client.testbedselection.event;

import com.google.gwt.event.shared.EventHandler;

public interface ConfigurationSelectedHandler extends EventHandler {

    void onTestbedConfigurationSelected(ConfigurationSelectedEvent event);
}
