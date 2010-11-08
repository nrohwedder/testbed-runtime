package de.uniluebeck.itm.webui.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import de.uniluebeck.itm.webui.client.activity.LoginActivity;
import de.uniluebeck.itm.webui.client.activity.NavigationActivity;
import de.uniluebeck.itm.webui.client.mvp.AppPlaceHistoryMapper;
import de.uniluebeck.itm.webui.client.mvp.ContentActivityManager;
import de.uniluebeck.itm.webui.client.mvp.ContentActivityMapper;
import de.uniluebeck.itm.webui.client.mvp.NavigationActivityManager;
import de.uniluebeck.itm.webui.client.place.LoginPlace;
import de.uniluebeck.itm.webui.client.ui.AppWidget;
import de.uniluebeck.itm.webui.client.ui.AppWidgetImpl;
import de.uniluebeck.itm.webui.client.ui.LoginView;
import de.uniluebeck.itm.webui.client.ui.LoginViewImpl;
import de.uniluebeck.itm.webui.client.ui.NavigationView;
import de.uniluebeck.itm.webui.client.ui.NavigationViewImpl;

public class WebUiClientModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(ContentActivityMapper.class);
		bind(AppWidget.class).to(AppWidgetImpl.class).in(Singleton.class);
		bind(NavigationView.class).to(NavigationViewImpl.class).in(Singleton.class);
		bind(LoginView.class).to(LoginViewImpl.class).in(Singleton.class);
		bind(LoginActivity.class);
		bind(NavigationActivity.class);
		bind(ContentActivityManager.class).in(Singleton.class);
		bind(NavigationActivityManager.class).in(Singleton.class);
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
		historyHandler.register(placeController, eventBus, new LoginPlace("World!"));
		return historyHandler;
	}
	
	@Singleton
	@Provides
	PlaceController providePlaceController(EventBus eventBus) {
		return new PlaceController(eventBus);
	}
}
