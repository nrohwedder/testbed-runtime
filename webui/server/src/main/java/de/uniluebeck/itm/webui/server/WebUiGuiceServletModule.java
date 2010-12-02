package de.uniluebeck.itm.webui.server;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

import de.uniluebeck.itm.webui.server.rpc.SNAAServiceImpl;
import de.uniluebeck.itm.webui.server.rpc.SessionManagementServiceImpl;
import de.uniluebeck.itm.webui.server.rpc.TestbedConfigurationServiceImpl;

public class WebUiGuiceServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        bind(Mapper.class).to(DozerBeanMapper.class).in(Singleton.class);
        
        serve("/webui/testbed.rpc").with(TestbedConfigurationServiceImpl.class);
        serve("/webui/snaa.rpc").with(SNAAServiceImpl.class);
        serve("/webui/sessionmanagement.rpc").with(SessionManagementServiceImpl.class);
    }
}
