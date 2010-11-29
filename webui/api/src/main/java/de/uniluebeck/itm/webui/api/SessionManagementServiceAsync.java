package de.uniluebeck.itm.webui.api;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uniluebeck.itm.webui.shared.wiseml.Wiseml;

public interface SessionManagementServiceAsync {
    
    void getWiseml(String url, AsyncCallback<Wiseml> callback);
}
