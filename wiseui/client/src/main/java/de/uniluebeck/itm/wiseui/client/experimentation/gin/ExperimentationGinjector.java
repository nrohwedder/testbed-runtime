package de.uniluebeck.itm.wiseui.client.experimentation.gin;

import com.google.gwt.inject.client.Ginjector;

import de.uniluebeck.itm.wiseui.client.experimentation.ExperimentationActivity;
import de.uniluebeck.itm.wiseui.client.experimentation.view.ExperimentationView;

public interface ExperimentationGinjector extends Ginjector {

	ExperimentationView getExperimentationView();
	
	ExperimentationActivity getExperimentationActivity();
}
