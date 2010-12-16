package de.uniluebeck.itm.wiseui.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;

import de.uniluebeck.itm.wiseui.client.administration.AdministrationActivity;
import de.uniluebeck.itm.wiseui.client.administration.view.AdministrationView;
import de.uniluebeck.itm.wiseui.client.experimentation.ExperimentationActivity;
import de.uniluebeck.itm.wiseui.client.experimentation.view.ExperimentationView;
import de.uniluebeck.itm.wiseui.client.navigation.NavigationActivity;
import de.uniluebeck.itm.wiseui.client.navigation.view.NavigationView;
import de.uniluebeck.itm.wiseui.client.reservation.ReservationActivity;
import de.uniluebeck.itm.wiseui.client.reservation.view.ReservationView;
import de.uniluebeck.itm.wiseui.client.testbedselection.TestbedSelectionActivity;
import de.uniluebeck.itm.wiseui.client.activity.ContentActivityManager;
import de.uniluebeck.itm.wiseui.client.activity.NavigationActivityManager;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.ConfigurationPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.DetailPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.LoginDialogPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.TestbedSelectionPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.NetworkPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.*;
import de.uniluebeck.itm.wiseui.client.main.view.WiseUiView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.ConfigurationView;

@GinModules(WiseUiClientModule.class)
public interface WiseUiGinjector extends Ginjector {

    EventBus getEventBus();

    /* 
     * Views 
     */
    WiseUiView getAppWidget();

    TestbedSelectionView getTestbedSelectionView();

    ReservationView getReservationView();

    NavigationView getNavigationView();

    ExperimentationView getExperimentationView();

    AdministrationView getAdministrationView();

    /* 
     * Activities 
     */
    NavigationActivityManager getNavigationActivityManager();

    ContentActivityManager getContentActivityManager();

    TestbedSelectionActivity getTestbedSelectionActivity();

    ReservationActivity getReservationActivity();

    NavigationActivity getNavigationActivity();

    ExperimentationActivity getExperimentationActivity();

    AdministrationActivity getAdministrationActivity();

    /*
     * Places
     */
    PlaceHistoryHandler getPlaceHistoryHandler();

    PlaceController getPlaceController();

    /*
    * Login
    */
    ConfigurationPresenter getConfigurationPresenter();

    TestbedSelectionPresenter getTestbedSelectionPresenter();

    DetailPresenter getDetailPresenter();

    NetworkPresenter getNetworkPresenter();

    LoginDialogPresenter getLoginDialogPresenter();

    ConfigurationView getConfigurationView();

    DetailView getDetailView();

    NetworkView getNetworkView();

    LoginDialogView getLoginDialogView();
}
