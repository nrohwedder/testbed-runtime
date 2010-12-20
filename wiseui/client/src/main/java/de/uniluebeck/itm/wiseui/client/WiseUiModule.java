package de.uniluebeck.itm.wiseui.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import de.uniluebeck.itm.wiseui.client.activity.ContentActivityManager;
import de.uniluebeck.itm.wiseui.client.activity.ContentActivityMapper;
import de.uniluebeck.itm.wiseui.client.activity.NavigationActivityManager;
import de.uniluebeck.itm.wiseui.client.activity.NavigationActivityMapper;
import de.uniluebeck.itm.wiseui.client.activity.WiseUiPlaceHistoryMapper;
import de.uniluebeck.itm.wiseui.client.main.view.WiseUiView;
import de.uniluebeck.itm.wiseui.client.main.view.WiseUiViewImpl;
import de.uniluebeck.itm.wiseui.client.testbedselection.TestbedSelectionPlace;

public class WiseUiModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(ContentActivityMapper.class);
        bind(ContentActivityManager.class).in(Singleton.class);
        bind(NavigationActivityMapper.class);
        bind(NavigationActivityManager.class).in(Singleton.class);

        // View binding
        bind(WiseUiView.class).to(WiseUiViewImpl.class).in(Singleton.class);
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
