package de.uniluebeck.itm.wiseui.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import de.uniluebeck.itm.wiseui.client.activity.AdministrationActivity;
import de.uniluebeck.itm.wiseui.client.activity.ExperimentationActivity;
import de.uniluebeck.itm.wiseui.client.activity.LoginActivity;
import de.uniluebeck.itm.wiseui.client.activity.NavigationActivity;
import de.uniluebeck.itm.wiseui.client.activity.ReservationActivity;
import de.uniluebeck.itm.wiseui.client.activity.WiseMLNativeActivity;
import de.uniluebeck.itm.wiseui.client.mvp.ContentActivityManager;
import de.uniluebeck.itm.wiseui.client.mvp.ContentActivityMapper;
import de.uniluebeck.itm.wiseui.client.mvp.NavigationActivityManager;
import de.uniluebeck.itm.wiseui.client.mvp.WiseUiPlaceHistoryMapper;
import de.uniluebeck.itm.wiseui.client.place.LoginPlace;
import de.uniluebeck.itm.wiseui.client.presenter.login.ConfigurationPresenter;
import de.uniluebeck.itm.wiseui.client.presenter.login.DetailPresenter;
import de.uniluebeck.itm.wiseui.client.presenter.login.LoginDialogPresenter;
import de.uniluebeck.itm.wiseui.client.presenter.login.NetworkPresenter;
import de.uniluebeck.itm.wiseui.client.ui.*;
import de.uniluebeck.itm.wiseui.client.ui.WiseUiView;
import de.uniluebeck.itm.wiseui.client.ui.login.ConfigurationView;
import de.uniluebeck.itm.wiseui.client.ui.login.ConfigurationViewImpl;
import de.uniluebeck.itm.wiseui.client.ui.login.DetailView;
import de.uniluebeck.itm.wiseui.client.ui.login.DetailViewImpl;
import de.uniluebeck.itm.wiseui.client.ui.login.LoginDialogView;
import de.uniluebeck.itm.wiseui.client.ui.login.LoginDialogViewImpl;
import de.uniluebeck.itm.wiseui.client.ui.login.LoginView;
import de.uniluebeck.itm.wiseui.client.ui.login.LoginViewImpl;
import de.uniluebeck.itm.wiseui.client.ui.login.NetworkView;
import de.uniluebeck.itm.wiseui.client.ui.login.NetworkViewImpl;

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
        bind(LoginView.class).to(LoginViewImpl.class).in(Singleton.class);
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
        bind(LoginActivity.class);
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
        historyHandler.register(placeController, eventBus, new LoginPlace());
        return historyHandler;
    }

    @Singleton
    @Provides
    PlaceController providePlaceController(final EventBus eventBus) {
        return new PlaceController(eventBus);
    }
}
