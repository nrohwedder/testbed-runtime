package de.uniluebeck.itm.webui.server;

import com.google.inject.servlet.ServletModule;

public class WebUiGuiceServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        bind(TestbedConfigurationService.class).to(XmlFileTestbedConfigurationService.class);

        serve("/webui/testbed.rpc").with(TestbedServiceImpl.class);
    }
}
