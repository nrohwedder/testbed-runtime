package de.uniluebeck.itm.wiseui.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.itm.uniluebeck.tr.wiseml.WiseMLHelper;
import de.uniluebeck.itm.wiseui.api.SessionManagementService;
import de.uniluebeck.itm.wiseui.shared.wiseml.Wiseml;
import eu.wisebed.testbed.api.wsn.WSNServiceHelper;
import eu.wisebed.testbed.api.wsn.v211.SessionManagement;
import org.dozer.Mapper;

@Singleton
public class SessionManagementServiceImpl extends RemoteServiceServlet implements SessionManagementService {

    private static final long serialVersionUID = 784455164992864141L;
    private final Mapper mapper;

    @Inject
    public SessionManagementServiceImpl(final Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Wiseml getWiseml(final String url) {
        final SessionManagement sessionManagement = WSNServiceHelper.getSessionManagementService(url);
        final eu.wisebed.ns.wiseml._1.Wiseml wiseml = WiseMLHelper.deserialize(sessionManagement.getNetwork());
        return mapper.map(wiseml, Wiseml.class);
    }
}
