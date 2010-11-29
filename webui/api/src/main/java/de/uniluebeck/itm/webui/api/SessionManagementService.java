package de.uniluebeck.itm.webui.api;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.uniluebeck.itm.webui.shared.wiseml.Wiseml;

@RemoteServiceRelativePath("sessionmanagement.rpc")
public interface SessionManagementService extends RemoteService {
    
    Wiseml getWiseml(String urn);
}
