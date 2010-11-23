package de.uniluebeck.itm.webui.api;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uniluebeck.itm.webui.shared.TestbedConfiguration;

public interface TestbedServiceAsync {
    
    void getConfigurations(AsyncCallback<List<TestbedConfiguration>> callback);
}
