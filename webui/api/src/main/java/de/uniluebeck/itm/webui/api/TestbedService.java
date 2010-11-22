package de.uniluebeck.itm.webui.api;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.uniluebeck.itm.webui.shared.NodeUrn;
import de.uniluebeck.itm.webui.shared.TestbedConfiguration;
import de.uniluebeck.itm.webui.shared.exception.AuthenticationException;

import java.util.List;


@RemoteServiceRelativePath("testbed.rpc")
public interface TestbedService extends RemoteService {

    List<TestbedConfiguration> getTestbedConfigurations();

    List<NodeUrn> getNetwork(String urn);

    void authenticate(String endpointUrl, String urn, String username, String password) throws AuthenticationException;
}
