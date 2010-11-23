package de.uniluebeck.itm.webui.api;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uniluebeck.itm.webui.shared.NodeUrn;

public interface SessionManagementServiceAsync {

    void getNetwork(String urn, AsyncCallback<List<NodeUrn>> callback);
}
