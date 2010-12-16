package de.uniluebeck.itm.wiseui.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;

import de.uniluebeck.itm.wiseui.client.activity.AdministrationActivity;
import de.uniluebeck.itm.wiseui.client.activity.ExperimentationActivity;
import de.uniluebeck.itm.wiseui.client.activity.LoginActivity;
import de.uniluebeck.itm.wiseui.client.activity.NavigationActivity;
import de.uniluebeck.itm.wiseui.client.activity.ReservationActivity;
import de.uniluebeck.itm.wiseui.client.activity.WiseMLNativeActivity;
import de.uniluebeck.itm.wiseui.client.mvp.ContentActivityManager;
import de.uniluebeck.itm.wiseui.client.mvp.NavigationActivityManager;
import de.uniluebeck.itm.wiseui.client.presenter.login.ConfigurationPresenter;
import de.uniluebeck.itm.wiseui.client.presenter.login.DetailPresenter;
import de.uniluebeck.itm.wiseui.client.presenter.login.LoginDialogPresenter;
import de.uniluebeck.itm.wiseui.client.presenter.login.LoginPresenter;
import de.uniluebeck.itm.wiseui.client.presenter.login.NetworkPresenter;
import de.uniluebeck.itm.wiseui.client.ui.*;
import de.uniluebeck.itm.wiseui.client.ui.WiseUiView;
import de.uniluebeck.itm.wiseui.client.ui.login.ConfigurationView;
import de.uniluebeck.itm.wiseui.client.ui.login.DetailView;
import de.uniluebeck.itm.wiseui.client.ui.login.LoginDialogView;
import de.uniluebeck.itm.wiseui.client.ui.login.LoginView;
import de.uniluebeck.itm.wiseui.client.ui.login.NetworkView;

@GinModules(WiseUiClientModule.class)
public interface WiseUiGinjector extends Ginjector {

    EventBus getEventBus();

    /* 
     * Views 
     */
    WiseUiView getAppWidget();

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

    /*
    * Login
    */
    ConfigurationPresenter getConfigurationPresenter();

    LoginPresenter getLoginPresenter();

    DetailPresenter getDetailPresenter();

    NetworkPresenter getNetworkPresenter();

    LoginDialogPresenter getLoginDialogPresenter();

    ConfigurationView getConfigurationView();

    DetailView getDetailView();

    NetworkView getNetworkView();

    LoginDialogView getLoginDialogView();
}
