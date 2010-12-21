package de.uniluebeck.itm.wiseui.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import de.uniluebeck.itm.wiseui.client.activity.ContentActivityManager;
import de.uniluebeck.itm.wiseui.client.activity.NavigationActivityManager;
import de.uniluebeck.itm.wiseui.client.activity.WiseUiPlaceHistoryMapper;
import de.uniluebeck.itm.wiseui.client.administration.view.AdministrationView;
import de.uniluebeck.itm.wiseui.client.administration.view.AdministrationViewImpl;
import de.uniluebeck.itm.wiseui.client.experimentation.view.ExperimentationView;
import de.uniluebeck.itm.wiseui.client.experimentation.view.ExperimentationViewImpl;
import de.uniluebeck.itm.wiseui.client.failure.view.FailureBoxView;
import de.uniluebeck.itm.wiseui.client.failure.view.FailureBoxViewImpl;
import de.uniluebeck.itm.wiseui.client.main.view.WiseUiView;
import de.uniluebeck.itm.wiseui.client.main.view.WiseUiViewImpl;
import de.uniluebeck.itm.wiseui.client.navigation.view.NavigationView;
import de.uniluebeck.itm.wiseui.client.navigation.view.NavigationViewImpl;
import de.uniluebeck.itm.wiseui.client.reservation.view.ReservationView;
import de.uniluebeck.itm.wiseui.client.reservation.view.ReservationViewImpl;
import de.uniluebeck.itm.wiseui.client.testbedselection.TestbedSelectionPlace;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.ConfigurationView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.ConfigurationViewImpl;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.DetailView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.DetailViewImpl;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.LoginDialogView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.LoginDialogViewImpl;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.NetworkView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.NetworkViewImpl;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.TestbedSelectionView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.TestbedSelectionViewImpl;

public class WiseUiClientModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(ContentActivityManager.class).in(Singleton.class);
        bind(NavigationActivityManager.class).in(Singleton.class);

        // View binding
        bind(WiseUiView.class).to(WiseUiViewImpl.class).in(Singleton.class);
        bind(NavigationView.class).to(NavigationViewImpl.class).in(Singleton.class);
        bind(TestbedSelectionView.class).to(TestbedSelectionViewImpl.class).in(Singleton.class);
        bind(ConfigurationView.class).to(ConfigurationViewImpl.class).in(Singleton.class);
        bind(DetailView.class).to(DetailViewImpl.class).in(Singleton.class);
        bind(NetworkView.class).to(NetworkViewImpl.class).in(Singleton.class);
        bind(LoginDialogView.class).to(LoginDialogViewImpl.class).in(Singleton.class);
        bind(ReservationView.class).to(ReservationViewImpl.class).in(Singleton.class);
        bind(ExperimentationView.class).to(ExperimentationViewImpl.class).in(Singleton.class);
        bind(AdministrationView.class).to(AdministrationViewImpl.class).in(Singleton.class);
        bind(FailureBoxView.class).to(FailureBoxViewImpl.class).in(Singleton.class);
    }

    @Singleton
    @Provides
    EventBus provideEventBus() {
        return new SimpleEventBus();
    }

    @Singleton
    @Provides
    PlaceHistoryHandler providePlaceHistoryHandler(
            final WiseUiPlaceHistoryMapper mapper,
            final PlaceController placeController, final EventBus eventBus) {
        final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
                mapper);
        historyHandler.register(placeController, eventBus, new TestbedSelectionPlace());
        return historyHandler;
    }

    @Singleton
    @Provides
    PlaceController providePlaceController(final EventBus eventBus) {
        return new PlaceController(eventBus);
    }
}
