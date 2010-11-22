package de.uniluebeck.itm.webui.api;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.uniluebeck.itm.webui.shared.NodeUrn;
import de.uniluebeck.itm.webui.shared.TestbedConfiguration;

import java.util.List;

public interface TestbedServiceAsync {
    void getTestbedConfigurations(AsyncCallback<List<TestbedConfiguration>> callback);

    void getNetwork(String urn, AsyncCallback<List<NodeUrn>> callback);

    void authenticate(String endpointUrl, String urn, String username, String password, AsyncCallback<Void> callback);
}
