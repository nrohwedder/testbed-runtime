package de.uniluebeck.itm.webui.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import de.uniluebeck.itm.webui.client.WebUiGinjector;
import de.uniluebeck.itm.webui.client.ui.ReservationView;
import de.uniluebeck.itm.webui.client.ui.ReservationView.Presenter;

public class ReservationActivity extends AbstractActivity implements Presenter {

	private final WebUiGinjector injector;
	
	private ReservationView view;
	
	@Inject
	public ReservationActivity(WebUiGinjector injector) {
		this.injector = injector;
	}

	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = injector.getReservationView();
		view.setPresenter(this);
		panel.setWidget(view);
	}

}
