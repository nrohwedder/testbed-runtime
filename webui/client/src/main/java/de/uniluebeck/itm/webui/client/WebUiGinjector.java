package de.uniluebeck.itm.webui.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;

import de.uniluebeck.itm.webui.client.activity.AdministrationActivity;
import de.uniluebeck.itm.webui.client.activity.ExperimentationActivity;
import de.uniluebeck.itm.webui.client.activity.LoginActivity;
import de.uniluebeck.itm.webui.client.activity.NavigationActivity;
import de.uniluebeck.itm.webui.client.activity.ReservationActivity;
import de.uniluebeck.itm.webui.client.activity.WiseMLNativeActivity;
import de.uniluebeck.itm.webui.client.mvp.ContentActivityManager;
import de.uniluebeck.itm.webui.client.mvp.NavigationActivityManager;
import de.uniluebeck.itm.webui.client.ui.AdministrationView;
import de.uniluebeck.itm.webui.client.ui.WebUiView;
import de.uniluebeck.itm.webui.client.ui.ExperimentationView;
import de.uniluebeck.itm.webui.client.ui.LoginView;
import de.uniluebeck.itm.webui.client.ui.NavigationView;
import de.uniluebeck.itm.webui.client.ui.ReservationView;
import de.uniluebeck.itm.webui.client.ui.WiseMLNativeView;

@GinModules(WebUiClientModule.class)
public interface WebUiGinjector extends Ginjector {

    EventBus getEventBus();

    /* 
     * Views 
     */
    WebUiView getAppWidget();

    LoginView getLoginView();

    ReservationView getReservationView();

    NavigationView getNavigationView();

    ExperimentationView getExperimentationView();

    AdministrationView getAdministrationView();

    WiseMLNativeView getWiseMLNativeView();

    /* 
     * Activities 
     */
    NavigationActivityManager getNavigationActivityManager();

    ContentActivityManager getContentActivityManager();

    LoginActivity getLoginActivity();

    ReservationActivity getReservationActivity();

    NavigationActivity getNavigationActivity();

    ExperimentationActivity getExperimentationActivity();

    AdministrationActivity getAdministrationActivity();

    WiseMLNativeActivity getWiseMLNativeActivity();

    /*
     * Places
     */
    PlaceHistoryHandler getPlaceHistoryHandler();

    PlaceController getPlaceController();
}
