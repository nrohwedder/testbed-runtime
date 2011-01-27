package de.uniluebeck.itm.wiseui.client.experimentation.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import de.uniluebeck.itm.wiseui.client.experimentation.ExperimentationActivity;
import de.uniluebeck.itm.wiseui.client.experimentation.view.ExperimentationView;
import de.uniluebeck.itm.wiseui.client.experimentation.view.ExperimentationViewImpl;

public class ExperimentationModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(ExperimentationView.class).to(ExperimentationViewImpl.class).in(Singleton.class);
		
		bind(ExperimentationActivity.class);
	}

}
