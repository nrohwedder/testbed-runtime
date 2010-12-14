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
import de.uniluebeck.itm.webui.client.presenter.login.ConfigurationPresenter;
import de.uniluebeck.itm.webui.client.presenter.login.DetailPresenter;
import de.uniluebeck.itm.webui.client.presenter.login.LoginDialogPresenter;
import de.uniluebeck.itm.webui.client.presenter.login.LoginPresenter;
import de.uniluebeck.itm.webui.client.presenter.login.NetworkPresenter;
import de.uniluebeck.itm.webui.client.ui.AdministrationView;
import de.uniluebeck.itm.webui.client.ui.ExperimentationView;
import de.uniluebeck.itm.webui.client.ui.NavigationView;
import de.uniluebeck.itm.webui.client.ui.ReservationView;
import de.uniluebeck.itm.webui.client.ui.WebUiView;
import de.uniluebeck.itm.webui.client.ui.WiseMLNativeView;
import de.uniluebeck.itm.webui.client.ui.login.ConfigurationView;
import de.uniluebeck.itm.webui.client.ui.login.DetailView;
import de.uniluebeck.itm.webui.client.ui.login.LoginDialogView;
import de.uniluebeck.itm.webui.client.ui.login.LoginView;
import de.uniluebeck.itm.webui.client.ui.login.NetworkView;

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
