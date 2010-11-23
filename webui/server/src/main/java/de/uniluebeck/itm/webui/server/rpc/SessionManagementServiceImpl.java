package de.uniluebeck.itm.webui.server.rpc;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Singleton;

import de.itm.uniluebeck.tr.wiseml.WiseMLHelper;
import de.uniluebeck.itm.webui.api.SessionManagementService;
import de.uniluebeck.itm.webui.shared.NodeUrn;
import eu.wisebed.testbed.api.wsn.WSNServiceHelper;
import eu.wisebed.testbed.api.wsn.v211.SessionManagement;

@Singleton
public class SessionManagementServiceImpl extends RemoteServiceServlet implements SessionManagementService {

    /**
     * 
     */
    private static final long serialVersionUID = 784455164992864141L;

    @Override
    public List<NodeUrn> getNetwork(final String url) {
        final SessionManagement sessionManagement = WSNServiceHelper.getSessionManagementService(url);
        final List<NodeUrn> list = new ArrayList<NodeUrn>();
        final List<String> nodes = WiseMLHelper.getNodeUrns(sessionManagement.getNetwork());

        for (String s : nodes) {
            final String[] n = s.split(":");
            if (n.length == 4) {
                list.add(new NodeUrn(n[0], n[1], n[2], n[3]));
            }
        }

        return list;
    }

}
