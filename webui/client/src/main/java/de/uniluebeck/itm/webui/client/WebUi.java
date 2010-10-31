package de.uniluebeck.itm.webui.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

public class WebUi implements EntryPoint {

	public final WebUiGinjector ginjector = GWT.create(WebUiGinjector.class);

    /**
     * This is the entry point method.
     */
	public void onModuleLoad() {

		DelayedBindRegistry.bind(ginjector);

		ginjector.getPlaceManager().revealCurrentPlace();
	}
}
