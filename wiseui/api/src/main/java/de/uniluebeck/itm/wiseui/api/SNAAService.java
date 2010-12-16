package de.uniluebeck.itm.wiseui.api;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.uniluebeck.itm.wiseui.shared.exception.AuthenticationException;
import de.uniluebeck.itm.wiseui.shared.wiseml.SecretAuthenticationKey;

@RemoteServiceRelativePath("snaa.rpc")
public interface SNAAService extends RemoteService {

    SecretAuthenticationKey authenticate(String endpointUrl, String urn, String username, String password) throws AuthenticationException;
}
