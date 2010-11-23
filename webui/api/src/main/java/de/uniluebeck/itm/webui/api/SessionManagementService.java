package de.uniluebeck.itm.webui.api;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.uniluebeck.itm.webui.shared.NodeUrn;

@RemoteServiceRelativePath("sessionmanagement.rpc")
public interface SessionManagementService extends RemoteService {

    List<NodeUrn> getNetwork(String urn);
}
