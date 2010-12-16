package de.uniluebeck.itm.wiseui.api;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.uniluebeck.itm.wiseui.shared.TestbedConfiguration;
import de.uniluebeck.itm.wiseui.shared.exception.ConfigurationException;


@RemoteServiceRelativePath("testbed.rpc")
public interface TestbedConfigurationService extends RemoteService {

    List<TestbedConfiguration> getConfigurations() throws ConfigurationException;
}
