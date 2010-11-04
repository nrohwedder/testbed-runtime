package de.uniluebeck.itm.webui.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import de.uniluebeck.itm.webui.client.activity.GoodbyeActivity;
import de.uniluebeck.itm.webui.client.activity.HelloActivity;
import de.uniluebeck.itm.webui.client.mvp.AppActivityMapper;
import de.uniluebeck.itm.webui.client.mvp.AppPlaceHistoryMapper;
import de.uniluebeck.itm.webui.client.place.HelloPlace;
import de.uniluebeck.itm.webui.client.ui.GoodbyeView;
import de.uniluebeck.itm.webui.client.ui.GoodbyeViewImpl;
import de.uniluebeck.itm.webui.client.ui.HelloView;
import de.uniluebeck.itm.webui.client.ui.HelloViewImpl;

public class WebUiClientModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(ActivityMapper.class).to(AppActivityMapper.class);
		bind(HelloView.class).to(HelloViewImpl.class).in(Singleton.class);
		bind(GoodbyeView.class).to(GoodbyeViewImpl.class).in(Singleton.class);
		bind(HelloActivity.class);
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
	PlaceHistoryHandler providePlaceHistoryHandler(PlaceHistoryMapper mapper, PlaceController placeController, EventBus eventBus) {
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(mapper);
		historyHandler.register(placeController, eventBus, new HelloPlace("World!"));
		return historyHandler;
	}
	
	@Singleton
	@Provides
	PlaceController providePlaceController(EventBus eventBus) {
		return new PlaceController(eventBus);
	}
	
	@Provides
	PlaceHistoryMapper providePlaceHistoryMapper() {
		return GWT.create(AppPlaceHistoryMapper.class);
	}
}
