package de.uniluebeck.itm.webui.api;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uniluebeck.itm.webui.shared.NodeUrn;
import de.uniluebeck.itm.webui.shared.TestbedConfiguration;

public interface TestbedServiceAsync {
	void getTestbedConfigurations(AsyncCallback<List<TestbedConfiguration>> callback);

	void getNetwork(String urn, AsyncCallback<List<NodeUrn>> callback);
}
