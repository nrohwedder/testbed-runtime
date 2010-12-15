package de.uniluebeck.itm.webui.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;

import de.uniluebeck.itm.webui.client.administration.AdministrationActivity;
import de.uniluebeck.itm.webui.client.administration.ui.AdministrationView;
import de.uniluebeck.itm.webui.client.experimentation.ExperimentationActivity;
import de.uniluebeck.itm.webui.client.experimentation.ui.ExperimentationView;
import de.uniluebeck.itm.webui.client.login.LoginActivity;
import de.uniluebeck.itm.webui.client.login.presenter.ConfigurationPresenter;
import de.uniluebeck.itm.webui.client.login.presenter.DetailPresenter;
import de.uniluebeck.itm.webui.client.login.presenter.LoginDialogPresenter;
import de.uniluebeck.itm.webui.client.login.presenter.LoginPresenter;
import de.uniluebeck.itm.webui.client.login.presenter.NetworkPresenter;
import de.uniluebeck.itm.webui.client.login.ui.ConfigurationView;
import de.uniluebeck.itm.webui.client.login.ui.DetailView;
import de.uniluebeck.itm.webui.client.login.ui.LoginDialogView;
import de.uniluebeck.itm.webui.client.login.ui.LoginView;
import de.uniluebeck.itm.webui.client.login.ui.NetworkView;
import de.uniluebeck.itm.webui.client.mvp.ContentActivityManager;
import de.uniluebeck.itm.webui.client.mvp.NavigationActivityManager;
import de.uniluebeck.itm.webui.client.navigation.NavigationActivity;
import de.uniluebeck.itm.webui.client.navigation.ui.NavigationView;
import de.uniluebeck.itm.webui.client.reservation.ReservationActivity;
import de.uniluebeck.itm.webui.client.reservation.ui.ReservationView;
import de.uniluebeck.itm.webui.client.ui.WebUiView;
import de.uniluebeck.itm.webui.client.wisemlnative.WiseMLNativeActivity;
import de.uniluebeck.itm.webui.client.wisemlnative.ui.WiseMLNativeView;

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
