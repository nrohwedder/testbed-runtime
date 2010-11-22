package de.uniluebeck.itm.webui.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import de.uniluebeck.itm.webui.client.activity.*;
import de.uniluebeck.itm.webui.client.mvp.AppPlaceHistoryMapper;
import de.uniluebeck.itm.webui.client.mvp.ContentActivityManager;
import de.uniluebeck.itm.webui.client.mvp.ContentActivityMapper;
import de.uniluebeck.itm.webui.client.mvp.NavigationActivityManager;
import de.uniluebeck.itm.webui.client.place.LoginPlace;
import de.uniluebeck.itm.webui.client.ui.*;

public class WebUiClientModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(ContentActivityMapper.class);
        bind(ContentActivityManager.class).in(Singleton.class);
        bind(NavigationActivityManager.class).in(Singleton.class);

        // View binding
        bind(AppWidget.class).to(AppWidgetImpl.class).in(Singleton.class);
        bind(NavigationView.class).to(NavigationViewImpl.class).in(Singleton.class);
        bind(LoginView.class).to(LoginViewImpl.class).in(Singleton.class);
        bind(ReservationView.class).to(ReservationViewImpl.class).in(Singleton.class);
        bind(ExperimentationView.class).to(ExperimentationViewImpl.class).in(Singleton.class);
        bind(AdministrationView.class).to(AdministrationViewImpl.class).in(Singleton.class);
        bind(WiseMLNativeView.class).to(WiseMLNativeViewImpl.class).in(Singleton.class);

        // Activitiy binding
        bind(LoginActivity.class);
        bind(NavigationActivity.class);
        bind(ReservationActivity.class);
        bind(ExperimentationActivity.class);
        bind(AdministrationActivity.class);
        bind(WiseMLNativeActivity.class);
    }

    @Singleton
    @Provides
    EventBus provideEventBus() {
        return new SimpleEventBus();
    }

    @Singleton
    @Provides
    PlaceHistoryHandler providePlaceHistoryHandler(AppPlaceHistoryMapper mapper, PlaceController placeController, EventBus eventBus) {
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(mapper);
        historyHandler.register(placeController, eventBus, new LoginPlace());
        return historyHandler;
    }

    @Singleton
    @Provides
    PlaceController providePlaceController(EventBus eventBus) {
        return new PlaceController(eventBus);
    }
}
