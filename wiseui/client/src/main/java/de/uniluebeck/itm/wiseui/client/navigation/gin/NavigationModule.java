package de.uniluebeck.itm.wiseui.client.navigation.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import de.uniluebeck.itm.wiseui.client.navigation.NavigationActivity;
import de.uniluebeck.itm.wiseui.client.navigation.view.NavigationView;
import de.uniluebeck.itm.wiseui.client.navigation.view.NavigationViewImpl;

public class NavigationModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(NavigationView.class).to(NavigationViewImpl.class).in(Singleton.class);

        bind(NavigationActivity.class);
	}

}
