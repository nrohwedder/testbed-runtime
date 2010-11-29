package de.uniluebeck.itm.webui.api;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.uniluebeck.itm.webui.shared.TestbedConfiguration;
import de.uniluebeck.itm.webui.shared.exception.ConfigurationException;


@RemoteServiceRelativePath("testbed.rpc")
public interface TestbedService extends RemoteService {

    List<TestbedConfiguration> getConfigurations() throws ConfigurationException;
}
