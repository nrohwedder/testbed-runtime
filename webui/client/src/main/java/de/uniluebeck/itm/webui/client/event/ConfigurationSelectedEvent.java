package de.uniluebeck.itm.webui.client.event;

import com.google.gwt.event.shared.GwtEvent;

import de.uniluebeck.itm.webui.shared.TestbedConfiguration;

public class ConfigurationSelectedEvent extends GwtEvent<ConfigurationSelectedHandler> {

    public static final Type<ConfigurationSelectedHandler> TYPE = new Type<ConfigurationSelectedHandler>();

    private final TestbedConfiguration configuration;
    
    public ConfigurationSelectedEvent(final TestbedConfiguration configuration) {
        this.configuration = configuration;
    }
    
    @Override
    protected void dispatch(final ConfigurationSelectedHandler handler) {
        handler.onTestbedConfigurationSelected(this);
    }

    @Override
    public Type<ConfigurationSelectedHandler> getAssociatedType() {
        return TYPE;
    }
    
    public TestbedConfiguration getConfiguration() {
        return configuration;
    }
}
