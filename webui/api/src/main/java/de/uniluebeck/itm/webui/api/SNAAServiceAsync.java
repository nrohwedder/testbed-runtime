package de.uniluebeck.itm.webui.api;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SNAAServiceAsync {
    
    void authenticate(String endpointUrl, String urn, String username, String password, AsyncCallback<Void> callback);
}
