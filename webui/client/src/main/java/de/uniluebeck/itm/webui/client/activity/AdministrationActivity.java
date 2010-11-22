package de.uniluebeck.itm.webui.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import de.uniluebeck.itm.webui.client.WebUiGinjector;
import de.uniluebeck.itm.webui.client.ui.AdministrationView;
import de.uniluebeck.itm.webui.client.ui.AdministrationView.Presenter;

public class AdministrationActivity extends AbstractActivity implements Presenter {

    private final WebUiGinjector injector;

    private AdministrationView view;

    @Inject
    public AdministrationActivity(final WebUiGinjector injector) {
        this.injector = injector;
    }

    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        view = injector.getAdministrationView();
        view.setPresenter(this);

        panel.setWidget(view);
    }
}
