package de.uniluebeck.itm.webui.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;

import de.uniluebeck.itm.webui.client.WebUiGinjector;
import de.uniluebeck.itm.webui.client.activity.NavigationActivity;

public class NavigationActivityMapper implements ActivityMapper {
	
	private final NavigationActivity activity;
	
	@Inject
	public NavigationActivityMapper(WebUiGinjector injector) {
		this.activity = injector.getNavigationActivity();
	}
	
	@Override
	public Activity getActivity(Place place) {
		activity.setPlace(place);
		return activity;
	}
}
