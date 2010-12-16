package de.uniluebeck.itm.wiseui.server;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

import de.uniluebeck.itm.wiseui.server.rpc.SNAAServiceImpl;
import de.uniluebeck.itm.wiseui.server.rpc.SessionManagementServiceImpl;
import de.uniluebeck.itm.wiseui.server.rpc.TestbedConfigurationServiceImpl;

public class WiseUiGuiceServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        bind(Mapper.class).to(DozerBeanMapper.class).in(Singleton.class);

        serve("/wiseui/testbed.rpc").with(TestbedConfigurationServiceImpl.class);
        serve("/wiseui/snaa.rpc").with(SNAAServiceImpl.class);
        serve("/wiseui/sessionmanagement.rpc").with(SessionManagementServiceImpl.class);
    }
}
