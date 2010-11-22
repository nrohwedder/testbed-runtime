package de.uniluebeck.itm.webui.client.mvp;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class NavigationActivityManager extends ActivityManager {

	@Inject
	public NavigationActivityManager(NavigationActivityMapper mapper, EventBus eventBus) {
		super(mapper, eventBus);
	}
}