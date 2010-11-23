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
import de.uniluebeck.itm.webui.client.ui.AppWidget;
import de.uniluebeck.itm.webui.client.ui.ExperimentationView;
import de.uniluebeck.itm.webui.client.ui.LoginView;
import de.uniluebeck.itm.webui.client.ui.NavigationView;
import de.uniluebeck.itm.webui.client.ui.ReservationView;
import de.uniluebeck.itm.webui.client.ui.WiseMLNativeView;

@GinModules(WebUiClientModule.class)
public interface WebUiGinjector extends Ginjector {
    EventBus getEventBus();

    PlaceController getPlaceController();

    LoginView getLoginView();

    ReservationView getReservationView();

    NavigationView getNavigationView();

    AppWidget getAppWidget();

    LoginActivity getLoginActivity();

    ReservationActivity getReservationActivity();

    NavigationActivity getNavigationActivity();

    NavigationActivityManager getNavigationActivityManager();

    ContentActivityManager getContentActivityManager();

    PlaceHistoryHandler getPlaceHistoryHandler();

    ExperimentationView getExperimentationView();

    AdministrationView getAdministrationView();

    WiseMLNativeView getWiseMLNativeView();

    ExperimentationActivity getExperimentationActivity();

    AdministrationActivity getAdministrationActivity();

    WiseMLNativeActivity getWiseMLNativeActivity();
}
