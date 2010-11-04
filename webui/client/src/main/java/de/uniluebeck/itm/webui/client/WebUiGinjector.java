package de.uniluebeck.itm.webui.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;

import de.uniluebeck.itm.webui.client.activity.GoodbyeActivity;
import de.uniluebeck.itm.webui.client.activity.HelloActivity;
import de.uniluebeck.itm.webui.client.ui.GoodbyeView;
import de.uniluebeck.itm.webui.client.ui.HelloView;

@GinModules(WebUiClientModule.class)
public interface WebUiGinjector extends Ginjector {
    EventBus getEventBus();

    PlaceController getPlaceController();

    HelloView getHelloView();

    HelloActivity getHelloActivity();
    
    GoodbyeView getGoodbyeView();
    
    GoodbyeActivity getGoodbyeActivity();
    
    ActivityManager getActivityManager();
    
    
    PlaceHistoryHandler getPlaceHistoryHandler();
}
