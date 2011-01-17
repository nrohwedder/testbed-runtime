package de.uniluebeck.itm.wiseui.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;

import de.uniluebeck.itm.wiseui.client.activity.ContentActivityManager;
import de.uniluebeck.itm.wiseui.client.activity.NavigationActivityManager;
import de.uniluebeck.itm.wiseui.client.administration.gin.AdministrationGinjector;
import de.uniluebeck.itm.wiseui.client.administration.gin.AdministrationModule;
import de.uniluebeck.itm.wiseui.client.experimentation.gin.ExperimentationGinjector;
import de.uniluebeck.itm.wiseui.client.experimentation.gin.ExperimentationModule;
import de.uniluebeck.itm.wiseui.client.main.view.WiseUiView;
import de.uniluebeck.itm.wiseui.client.navigation.gin.NavigationGinjector;
import de.uniluebeck.itm.wiseui.client.navigation.gin.NavigationModule;
import de.uniluebeck.itm.wiseui.client.reservation.gin.ReservationGinjector;
import de.uniluebeck.itm.wiseui.client.reservation.gin.ReservationModule;
import de.uniluebeck.itm.wiseui.client.testbedselection.gin.TestbedSelectionGinjector;
import de.uniluebeck.itm.wiseui.client.testbedselection.gin.TestbedSelectionModule;
import de.uniluebeck.itm.wiseui.client.util.AuthenticationManager;

@GinModules({
    WiseUiModule.class,
    NavigationModule.class,
    TestbedSelectionModule.class,
    ReservationModule.class,
    ExperimentationModule.class,
    AdministrationModule.class
})
public interface WiseUiGinjector extends Ginjector, NavigationGinjector, TestbedSelectionGinjector, ReservationGinjector, ExperimentationGinjector, AdministrationGinjector {

    AuthenticationManager getAuthenticationManager();

    EventBus getEventBus();

    WiseUiView getAppWidget();

    NavigationActivityManager getNavigationActivityManager();

    ContentActivityManager getContentActivityManager();

    PlaceHistoryHandler getPlaceHistoryHandler();

    PlaceController getPlaceController();
}
