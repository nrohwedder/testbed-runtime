package de.uniluebeck.itm.wiseui.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.itm.uniluebeck.tr.wiseml.WiseMLHelper;
import de.uniluebeck.itm.wiseui.api.SessionManagementService;
import de.uniluebeck.itm.wiseui.shared.exception.ConfigurationException;
import de.uniluebeck.itm.wiseui.shared.wiseml.Wiseml;
import eu.wisebed.testbed.api.wsn.WSNServiceHelper;
import eu.wisebed.testbed.api.wsn.v211.SessionManagement;
import org.dozer.Mapper;

@Singleton
public class SessionManagementServiceImpl extends RemoteServiceServlet implements SessionManagementService {

    private static final long serialVersionUID = 784455164992864141L;
    private static final String PARAMETER_URL_ERROR = "Parameter \"url\" is NULL or empty!";
    private static final String GET_NETWORK_ERROR = "getNetwork() did not return a valid XML String!";
    private final Mapper mapper;

    @Inject
    public SessionManagementServiceImpl(final Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Wiseml getWiseml(final String url) throws ConfigurationException {
        if (null == url || url.isEmpty()) throw new ConfigurationException(PARAMETER_URL_ERROR);

        final SessionManagement sessionManagement;
        final eu.wisebed.ns.wiseml._1.Wiseml wiseml;

        sessionManagement = WSNServiceHelper.getSessionManagementService(url);

        final String network = sessionManagement.getNetwork();
        if (network == null || network.isEmpty()) throw new ConfigurationException(GET_NETWORK_ERROR);

        wiseml = WiseMLHelper.deserialize(network);

        return mapper.map(wiseml, Wiseml.class);
    }
}
