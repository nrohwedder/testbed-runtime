package de.uniluebeck.itm.webui.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import de.uniluebeck.itm.webui.client.activity.GoodbyeActivity;
import de.uniluebeck.itm.webui.client.activity.LoginActivity;
import de.uniluebeck.itm.webui.client.mvp.AppActivityMapper;
import de.uniluebeck.itm.webui.client.mvp.AppPlaceHistoryMapper;
import de.uniluebeck.itm.webui.client.place.LoginPlace;
import de.uniluebeck.itm.webui.client.ui.GoodbyeView;
import de.uniluebeck.itm.webui.client.ui.GoodbyeViewImpl;
import de.uniluebeck.itm.webui.client.ui.LoginView;
import de.uniluebeck.itm.webui.client.ui.LoginViewImpl;

public class WebUiClientModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(ActivityMapper.class).to(AppActivityMapper.class);
		bind(LoginView.class).to(LoginViewImpl.class).in(Singleton.class);
		bind(GoodbyeView.class).to(GoodbyeViewImpl.class).in(Singleton.class);
		bind(LoginActivity.class);
		bind(GoodbyeActivity.class);
	}

	@Singleton
	@Provides
	EventBus provideEventBus() {
		return new SimpleEventBus();
	}
	
	@Singleton
	@Provides
	ActivityManager provideActivityManager(ActivityMapper mapper, EventBus eventBus) {
		return new ActivityManager(mapper, eventBus);
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
