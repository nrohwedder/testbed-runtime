package de.uniluebeck.itm.wiseui.client.reservation.gin;

import com.google.gwt.inject.client.Ginjector;

import de.uniluebeck.itm.wiseui.client.reservation.ReservationActivity;
import de.uniluebeck.itm.wiseui.client.reservation.view.ReservationView;

public interface ReservationGinjector extends Ginjector {

    ReservationActivity getReservationActivity();
	
    ReservationView getReservationView();
}
