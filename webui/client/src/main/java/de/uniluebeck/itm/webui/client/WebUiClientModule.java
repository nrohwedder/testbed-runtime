package de.uniluebeck.itm.webui.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import de.uniluebeck.itm.webui.client.administration.AdministrationActivity;
import de.uniluebeck.itm.webui.client.administration.ui.AdministrationView;
import de.uniluebeck.itm.webui.client.administration.ui.AdministrationViewImpl;
import de.uniluebeck.itm.webui.client.experimentation.ExperimentationActivity;
import de.uniluebeck.itm.webui.client.experimentation.ui.ExperimentationView;
import de.uniluebeck.itm.webui.client.experimentation.ui.ExperimentationViewImpl;
import de.uniluebeck.itm.webui.client.login.LoginActivity;
import de.uniluebeck.itm.webui.client.login.presenter.ConfigurationPresenter;
import de.uniluebeck.itm.webui.client.login.presenter.DetailPresenter;
import de.uniluebeck.itm.webui.client.login.presenter.LoginDialogPresenter;
import de.uniluebeck.itm.webui.client.login.presenter.NetworkPresenter;
import de.uniluebeck.itm.webui.client.login.ui.ConfigurationView;
import de.uniluebeck.itm.webui.client.login.ui.ConfigurationViewImpl;
import de.uniluebeck.itm.webui.client.login.ui.DetailView;
import de.uniluebeck.itm.webui.client.login.ui.DetailViewImpl;
import de.uniluebeck.itm.webui.client.login.ui.LoginDialogView;
import de.uniluebeck.itm.webui.client.login.ui.LoginDialogViewImpl;
import de.uniluebeck.itm.webui.client.login.ui.LoginView;
import de.uniluebeck.itm.webui.client.login.ui.LoginViewImpl;
import de.uniluebeck.itm.webui.client.login.ui.NetworkView;
import de.uniluebeck.itm.webui.client.login.ui.NetworkViewImpl;
import de.uniluebeck.itm.webui.client.mvp.ContentActivityManager;
import de.uniluebeck.itm.webui.client.mvp.ContentActivityMapper;
import de.uniluebeck.itm.webui.client.mvp.NavigationActivityManager;
import de.uniluebeck.itm.webui.client.mvp.WebUiPlaceHistoryMapper;
import de.uniluebeck.itm.webui.client.navigation.NavigationActivity;
import de.uniluebeck.itm.webui.client.navigation.ui.NavigationView;
import de.uniluebeck.itm.webui.client.navigation.ui.NavigationViewImpl;
import de.uniluebeck.itm.webui.client.place.LoginPlace;
import de.uniluebeck.itm.webui.client.reservation.ReservationActivity;
import de.uniluebeck.itm.webui.client.reservation.ui.ReservationView;
import de.uniluebeck.itm.webui.client.reservation.ui.ReservationViewImpl;
import de.uniluebeck.itm.webui.client.ui.WebUiView;
import de.uniluebeck.itm.webui.client.ui.WebUiViewImpl;
import de.uniluebeck.itm.webui.client.wisemlnative.WiseMLNativeActivity;
import de.uniluebeck.itm.webui.client.wisemlnative.ui.WiseMLNativeView;
import de.uniluebeck.itm.webui.client.wisemlnative.ui.WiseMLNativeViewImpl;

public class WebUiClientModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(ContentActivityMapper.class);
        bind(ContentActivityManager.class).in(Singleton.class);
        bind(NavigationActivityManager.class).in(Singleton.class);

        // View binding
        bind(WebUiView.class).to(WebUiViewImpl.class).in(Singleton.class);
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
            final WebUiPlaceHistoryMapper mapper,
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
