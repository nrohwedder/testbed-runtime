package de.uniluebeck.itm.webui.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import de.uniluebeck.itm.webui.client.WebUiGinjector;
import de.uniluebeck.itm.webui.client.ui.NavigationView;

public class NavigationActivity extends AbstractActivity implements NavigationView.Presenter {

	private WebUiGinjector injector;
	
	private NavigationView navigationView;
	
	@Inject
	public NavigationActivity(WebUiGinjector injector) {
		this.injector = injector;
	}
	
	@Override
	public void start(AcceptsOneWidget container,
			EventBus eventBus) {
		navigationView = injector.getNavigationView();
		navigationView.setPresenter(this);
		container.setWidget(navigationView);
	}

}
