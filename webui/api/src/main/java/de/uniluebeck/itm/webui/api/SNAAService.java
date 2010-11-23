package de.uniluebeck.itm.webui.api;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.uniluebeck.itm.webui.shared.exception.AuthenticationException;


@RemoteServiceRelativePath("snaa.rpc")
public interface SNAAService extends RemoteService {

    void authenticate(String endpointUrl, String urn, String username, String password) throws AuthenticationException;
}
