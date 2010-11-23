package de.uniluebeck.itm.webui.server;

import com.google.inject.servlet.ServletModule;

public class WebUiGuiceServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        serve("/webui/testbed.rpc").with(XmlFileTestbedService.class);
        serve("/webui/snaa.rpc").with(SNAAServiceImpl.class);
        serve("/webui/sessionmanagement.rpc").with(SessionManagementServiceImpl.class);
    }
}
