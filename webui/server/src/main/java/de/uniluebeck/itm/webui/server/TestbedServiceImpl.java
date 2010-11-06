package de.uniluebeck.itm.webui.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import de.itm.uniluebeck.tr.wiseml.WiseMLHelper;
import de.uniluebeck.itm.webui.api.TestbedService;
import de.uniluebeck.itm.webui.shared.NodeUrn;
import de.uniluebeck.itm.webui.shared.TestbedConfiguration;
import eu.wisebed.testbed.api.wsn.WSNServiceHelper;
import eu.wisebed.testbed.api.wsn.v211.SessionManagement;

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
	public List<NodeUrn> getNetwork(String url) {
		final SessionManagement sessionManagement = WSNServiceHelper.getSessionManagementService(url);
		final List<NodeUrn> list = new ArrayList<NodeUrn>();
        final List<String> nodes = WiseMLHelper.getNodeUrns(sessionManagement.getNetwork());

        for (String s : nodes) {
            String[] n = s.split(":");
            if (n.length == 4) {
                list.add(new NodeUrn(n[0], n[1], n[2], n[3]));
            }
        }

        return list;
	}

}
