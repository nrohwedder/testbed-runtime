package de.uniluebeck.itm.wiseui.api;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.uniluebeck.itm.wiseui.shared.TestbedConfiguration;
import de.uniluebeck.itm.wiseui.shared.exception.ConfigurationException;

import java.util.List;


@RemoteServiceRelativePath("testbed.rpc")
public interface TestbedConfigurationService extends RemoteService {

    List<TestbedConfiguration> getConfigurations() throws ConfigurationException;
}
