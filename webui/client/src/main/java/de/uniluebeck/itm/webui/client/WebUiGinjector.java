package de.uniluebeck.itm.webui.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;

import de.uniluebeck.itm.webui.client.activity.LoginActivity;
import de.uniluebeck.itm.webui.client.activity.NavigationActivity;
import de.uniluebeck.itm.webui.client.mvp.ContentActivityManager;
import de.uniluebeck.itm.webui.client.mvp.NavigationActivityManager;
import de.uniluebeck.itm.webui.client.ui.AppWidget;
import de.uniluebeck.itm.webui.client.ui.LoginView;
import de.uniluebeck.itm.webui.client.ui.NavigationView;

@GinModules(WebUiClientModule.class)
public interface WebUiGinjector extends Ginjector {
    EventBus getEventBus();

    PlaceController getPlaceController();

    LoginView getLoginView();
    
    NavigationView getNavigationView();
    
    AppWidget getAppWidget();

    LoginActivity getLoginActivity();
    
    NavigationActivity getNavigationActivity();
    
    NavigationActivityManager getNavigationActivityManager();
    
    ContentActivityManager getContentActivityManager();
    
    PlaceHistoryHandler getPlaceHistoryHandler();
}
