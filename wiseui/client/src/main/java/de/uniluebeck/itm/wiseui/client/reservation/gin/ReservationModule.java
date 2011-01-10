package de.uniluebeck.itm.wiseui.client.reservation.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import de.uniluebeck.itm.wiseui.client.reservation.ReservationActivity;
import de.uniluebeck.itm.wiseui.client.reservation.view.ReservationView;
import de.uniluebeck.itm.wiseui.client.reservation.view.ReservationViewImpl;

public class ReservationModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(ReservationView.class).to(ReservationViewImpl.class).in(Singleton.class);
		
		bind(ReservationActivity.class);
	}

}
