package de.uniluebeck.itm.wiseui.client.administration.gin;

import com.google.gwt.inject.client.Ginjector;

import de.uniluebeck.itm.wiseui.client.administration.AdministrationActivity;
import de.uniluebeck.itm.wiseui.client.administration.view.AdministrationView;

public interface AdministrationGinjector extends Ginjector {

	AdministrationView getAdministrationView();
	
	AdministrationActivity getAdministrationActivity();
}
