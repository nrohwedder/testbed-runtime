package de.uniluebeck.itm.webui.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import de.uniluebeck.itm.webui.api.TestbedService;
import de.uniluebeck.itm.webui.shared.NodeUrn;
import de.uniluebeck.itm.webui.shared.TestbedConfiguration;

@Singleton
public class TestbedServiceImpl extends RemoteServiceServlet implements TestbedService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5174874924600302509L;
	
	@Inject
	private Injector injector;
	
	@Override
	public List<TestbedConfiguration> getTestbedConfigurations() {
		TestbedConfigurationService service = injector.getInstance(TestbedConfigurationService.class);
		return service.getConfigurations();
	}

	@Override
	public List<NodeUrn> getNetwork(String urn) {
		
		return null;
	}

}
