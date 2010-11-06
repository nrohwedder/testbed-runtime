package de.uniluebeck.itm.webui.api;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.uniluebeck.itm.webui.shared.NodeUrn;
import de.uniluebeck.itm.webui.shared.TestbedConfiguration;


@RemoteServiceRelativePath("testbed.rpc")
public interface TestbedService extends RemoteService {

	List<TestbedConfiguration> getTestbedConfigurations();
	
	List<NodeUrn> getNetwork(String urn);
}
