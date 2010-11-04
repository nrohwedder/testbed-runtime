package de.uniluebeck.itm.webui.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WebUi implements EntryPoint {

    private final SimplePanel appWidget = new SimplePanel();
    
    private final WebUiGinjector injector = GWT.create(WebUiGinjector.class);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        // Start ActivityManager for the main widget with our ActivityMapper
        injector.getActivityManager().setDisplay(appWidget);

        RootPanel.get().add(appWidget);
        // Goes to place represented on URL or default place
        injector.getPlaceHistoryHandler().handleCurrentHistory();
    }
}
