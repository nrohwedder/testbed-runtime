package de.uniluebeck.itm.wiseui.client.experiment;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import de.uniluebeck.itm.wiseui.client.WiseUiGinjector;
import de.uniluebeck.itm.wiseui.client.experiment.view.ExperimentationView;
import de.uniluebeck.itm.wiseui.client.experiment.view.ExperimentationView.Presenter;

public class ExperimentationActivity extends AbstractActivity implements
        Presenter {

    private final WiseUiGinjector injector;

    private ExperimentationView view;

    @Inject
    public ExperimentationActivity(final WiseUiGinjector injector) {
        this.injector = injector;
    }

    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        view = injector.getExperimentationView();
        view.setPresenter(this);

        panel.setWidget(view);
    }

}
