package de.uniluebeck.itm.webui.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import de.uniluebeck.itm.webui.client.WebUiGinjector;
import de.uniluebeck.itm.webui.client.ui.ExperimentationView;
import de.uniluebeck.itm.webui.client.ui.ExperimentationView.Presenter;

public class ExperimentationActivity extends AbstractActivity implements Presenter {

    private final WebUiGinjector injector;

    private ExperimentationView view;

    @Inject
    public ExperimentationActivity(final WebUiGinjector injector) {
        this.injector = injector;
    }

    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        view = injector.getExperimentationView();
        view.setPresenter(this);

        panel.setWidget(view);
    }

}
