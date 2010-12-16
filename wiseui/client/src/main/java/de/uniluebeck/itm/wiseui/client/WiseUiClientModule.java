package de.uniluebeck.itm.wiseui.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import de.uniluebeck.itm.wiseui.client.administration.AdministrationActivity;
import de.uniluebeck.itm.wiseui.client.administration.view.AdministrationView;
import de.uniluebeck.itm.wiseui.client.administration.view.AdministrationViewImpl;
import de.uniluebeck.itm.wiseui.client.experiment.ExperimentationActivity;
import de.uniluebeck.itm.wiseui.client.experiment.view.ExperimentationView;
import de.uniluebeck.itm.wiseui.client.experiment.view.ExperimentationViewImpl;
import de.uniluebeck.itm.wiseui.client.main.view.WiseUiViewImpl;
import de.uniluebeck.itm.wiseui.client.navigation.NavigationActivity;
import de.uniluebeck.itm.wiseui.client.navigation.view.NavigationView;
import de.uniluebeck.itm.wiseui.client.navigation.view.NavigationViewImpl;
import de.uniluebeck.itm.wiseui.client.reservation.ReservationActivity;
import de.uniluebeck.itm.wiseui.client.reservation.view.ReservationView;
import de.uniluebeck.itm.wiseui.client.reservation.view.ReservationViewImpl;
import de.uniluebeck.itm.wiseui.client.testbedselection.TestbedSelectionActivity;
import de.uniluebeck.itm.wiseui.client.activity.ContentActivityManager;
import de.uniluebeck.itm.wiseui.client.activity.ContentActivityMapper;
import de.uniluebeck.itm.wiseui.client.activity.NavigationActivityManager;
import de.uniluebeck.itm.wiseui.client.activity.WiseUiPlaceHistoryMapper;
import de.uniluebeck.itm.wiseui.client.testbedselection.TestbedSelectionPlace;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.ConfigurationPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.DetailPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.LoginDialogPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.NetworkPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.*;
import de.uniluebeck.itm.wiseui.client.main.view.WiseUiView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.ConfigurationView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.ConfigurationViewImpl;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.LoginDialogView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.TestbedSelectionViewImpl;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.NetworkView;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.NetworkViewImpl;

public class WiseUiClientModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(ContentActivityMapper.class);
        bind(ContentActivityManager.class).in(Singleton.class);
        bind(NavigationActivityManager.class).in(Singleton.class);

        // View binding
        bind(WiseUiView.class).to(WiseUiViewImpl.class).in(Singleton.class);
        bind(NavigationView.class).to(NavigationViewImpl.class).in(
                Singleton.class);
        bind(TestbedSelectionView.class).to(TestbedSelectionViewImpl.class).in(Singleton.class);
        bind(ConfigurationView.class).to(ConfigurationViewImpl.class).in(Singleton.class);
        bind(DetailView.class).to(DetailViewImpl.class).in(Singleton.class);
        bind(NetworkView.class).to(NetworkViewImpl.class).in(Singleton.class);
        bind(LoginDialogView.class).to(LoginDialogViewImpl.class).in(Singleton.class);
        bind(ReservationView.class).to(ReservationViewImpl.class).in(
                Singleton.class);
        bind(ExperimentationView.class).to(ExperimentationViewImpl.class).in(
                Singleton.class);
        bind(AdministrationView.class).to(AdministrationViewImpl.class).in(
                Singleton.class);
        bind(WiseMLNativeView.class).to(WiseMLNativeViewImpl.class).in(
                Singleton.class);

        // Activitiy binding
        bind(TestbedSelectionActivity.class);
        bind(NavigationActivity.class);
        bind(ReservationActivity.class);
        bind(ExperimentationActivity.class);
        bind(AdministrationActivity.class);
        bind(WiseMLNativeActivity.class);

        // Presenter binding
        bind(ConfigurationPresenter.class);
        bind(NetworkPresenter.class);
        bind(DetailPresenter.class);
        bind(LoginDialogPresenter.class);
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
