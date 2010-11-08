package de.uniluebeck.itm.webui.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

import de.uniluebeck.itm.webui.client.ui.AppWidget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WebUi implements EntryPoint {
    
    private final WebUiGinjector injector = GWT.create(WebUiGinjector.class);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
    	final AppWidget appWidget = injector.getAppWidget();
    	
    	injector.getNavigationActivityManager().setDisplay(appWidget.getNavigationPanel());
    	
        // Start ActivityManager for the main widget with our ActivityMapper
        injector.getContentActivityManager().setDisplay(appWidget.getContentPanel());

        RootPanel.get().add(appWidget.asWidget());
        // Goes to place represented on URL or default place
        injector.getPlaceHistoryHandler().handleCurrentHistory();
    }
}
