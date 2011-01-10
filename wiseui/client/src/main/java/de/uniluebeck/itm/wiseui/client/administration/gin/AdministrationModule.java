package de.uniluebeck.itm.wiseui.client.administration.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import de.uniluebeck.itm.wiseui.client.administration.AdministrationActivity;
import de.uniluebeck.itm.wiseui.client.administration.view.AdministrationView;
import de.uniluebeck.itm.wiseui.client.administration.view.AdministrationViewImpl;

public class AdministrationModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(AdministrationView.class).to(AdministrationViewImpl.class).in(Singleton.class);
		
		bind(AdministrationActivity.class);
	}

}
