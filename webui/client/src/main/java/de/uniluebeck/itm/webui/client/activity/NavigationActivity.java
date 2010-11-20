package de.uniluebeck.itm.webui.client.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import de.uniluebeck.itm.webui.client.WebUiGinjector;
import de.uniluebeck.itm.webui.client.place.LoginPlace;
import de.uniluebeck.itm.webui.client.place.ReservationPlace;
import de.uniluebeck.itm.webui.client.ui.NavigationView;

public class NavigationActivity extends AbstractActivity implements NavigationView.Presenter {
	
	private class Entry {
		private Place place;
		
		private String name;
		
		public Entry(String name, Place place) {
			this.name = name;
			this.place = place;
		}
		
		public String getName() {
			return name;
		}
		
		public Place getPlace() {
			return place;
		}
	}
	
	private WebUiGinjector injector;
	
	private NavigationView navigationView;
	
	private List<Entry> navigation = new ArrayList<Entry>();
	
	private Place place;
	
	@Inject
	public NavigationActivity(WebUiGinjector injector) {
		this.injector = injector;
		
		navigation.add(new Entry("Login", new LoginPlace()));
		navigation.add(new Entry("Reservation", new ReservationPlace()));
	}
	
	@Override
	public void start(AcceptsOneWidget container, EventBus eventBus) {
		navigationView = injector.getNavigationView();
		navigationView.setPresenter(this);
		
		initTabs();
		
		container.setWidget(navigationView);
	}
	
	private void initTabs() {
		int i = 0;
		for (Entry entry : navigation) {
			navigationView.add(entry.getName());
			if (place != null && place.getClass().equals(entry.getPlace().getClass())) {
				navigationView.select(i);
			}
			i++;
		}
		if (place == null) {
			navigationView.select(0);
		}
	}
	
	@Override
	public void selected(Integer index) {
		Entry entry = navigation.get(index);
		GWT.log("Go to place: " + entry.getName());
		injector.getPlaceController().goTo(entry.getPlace());
	}

	public void setPlace(Place place) {
		this.place = place;
	}
}
