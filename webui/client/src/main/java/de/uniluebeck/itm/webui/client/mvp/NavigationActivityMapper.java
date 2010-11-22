package de.uniluebeck.itm.webui.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;

import de.uniluebeck.itm.webui.client.WebUiGinjector;

public class NavigationActivityMapper implements ActivityMapper {
	
	private final WebUiGinjector injector;
	
	@Inject
	public NavigationActivityMapper(WebUiGinjector injector) {
		this.injector = injector;
	}

	public Activity getActivity(Place place) {
		return injector.getNavigationActivity();
	}
}
