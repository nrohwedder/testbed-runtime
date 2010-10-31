package de.uniluebeck.itm.webui.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

public class WebUi implements EntryPoint {

	public final MyGinjector ginjector = GWT.create(MyGinjector.class);

    /**
     * This is the entry point method.
     */
	public void onModuleLoad() {

		DelayedBindRegistry.bind(ginjector);

		ginjector.getPlaceManager().revealCurrentPlace();
	}
}
