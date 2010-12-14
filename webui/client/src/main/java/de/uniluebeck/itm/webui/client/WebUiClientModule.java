package de.uniluebeck.itm.webui.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import de.uniluebeck.itm.webui.client.activity.AdministrationActivity;
import de.uniluebeck.itm.webui.client.activity.ExperimentationActivity;
import de.uniluebeck.itm.webui.client.activity.LoginActivity;
import de.uniluebeck.itm.webui.client.activity.NavigationActivity;
import de.uniluebeck.itm.webui.client.activity.ReservationActivity;
import de.uniluebeck.itm.webui.client.activity.WiseMLNativeActivity;
import de.uniluebeck.itm.webui.client.mvp.ContentActivityManager;
import de.uniluebeck.itm.webui.client.mvp.ContentActivityMapper;
import de.uniluebeck.itm.webui.client.mvp.NavigationActivityManager;
import de.uniluebeck.itm.webui.client.mvp.WebUiPlaceHistoryMapper;
import de.uniluebeck.itm.webui.client.place.LoginPlace;
import de.uniluebeck.itm.webui.client.presenter.login.ConfigurationPresenter;
import de.uniluebeck.itm.webui.client.presenter.login.DetailPresenter;
import de.uniluebeck.itm.webui.client.presenter.login.LoginDialogPresenter;
import de.uniluebeck.itm.webui.client.presenter.login.NetworkPresenter;
import de.uniluebeck.itm.webui.client.ui.AdministrationView;
import de.uniluebeck.itm.webui.client.ui.AdministrationViewImpl;
import de.uniluebeck.itm.webui.client.ui.ExperimentationView;
import de.uniluebeck.itm.webui.client.ui.ExperimentationViewImpl;
import de.uniluebeck.itm.webui.client.ui.NavigationView;
import de.uniluebeck.itm.webui.client.ui.NavigationViewImpl;
import de.uniluebeck.itm.webui.client.ui.ReservationView;
import de.uniluebeck.itm.webui.client.ui.ReservationViewImpl;
import de.uniluebeck.itm.webui.client.ui.WebUiView;
import de.uniluebeck.itm.webui.client.ui.WebUiViewImpl;
import de.uniluebeck.itm.webui.client.ui.WiseMLNativeView;
import de.uniluebeck.itm.webui.client.ui.WiseMLNativeViewImpl;
import de.uniluebeck.itm.webui.client.ui.login.ConfigurationView;
import de.uniluebeck.itm.webui.client.ui.login.ConfigurationViewImpl;
import de.uniluebeck.itm.webui.client.ui.login.DetailView;
import de.uniluebeck.itm.webui.client.ui.login.DetailViewImpl;
import de.uniluebeck.itm.webui.client.ui.login.LoginDialogView;
import de.uniluebeck.itm.webui.client.ui.login.LoginDialogViewImpl;
import de.uniluebeck.itm.webui.client.ui.login.LoginView;
import de.uniluebeck.itm.webui.client.ui.login.LoginViewImpl;
import de.uniluebeck.itm.webui.client.ui.login.NetworkView;
import de.uniluebeck.itm.webui.client.ui.login.NetworkViewImpl;

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
