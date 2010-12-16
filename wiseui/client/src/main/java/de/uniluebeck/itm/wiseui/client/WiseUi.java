package de.uniluebeck.itm.wiseui.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import de.uniluebeck.itm.wiseui.client.main.view.WiseUiView;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WiseUi implements EntryPoint {

    private final WiseUiGinjector injector = GWT.create(WiseUiGinjector.class);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final WiseUiView appWidget = injector.getAppWidget();

        injector.getNavigationActivityManager().setDisplay(
                appWidget.getNavigationPanel());

        // Start ActivityManager for the main widget with our ActivityMapper
        injector.getContentActivityManager().setDisplay(
                appWidget.getContentPanel());

        RootPanel.get().add(appWidget.asWidget());

        // Goes to place represented on URL or default place
        injector.getPlaceHistoryHandler().handleCurrentHistory();
    }
}
