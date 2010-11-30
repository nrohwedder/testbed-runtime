package de.uniluebeck.itm.webui.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import de.uniluebeck.itm.webui.client.WebUiGinjector;
import de.uniluebeck.itm.webui.client.ui.AdministrationView;
import de.uniluebeck.itm.webui.client.ui.AdministrationView.Presenter;

public class AdministrationActivity extends AbstractActivity implements Presenter {

    private AdministrationView view;

    @Inject
    public AdministrationActivity(final AdministrationView view) {
        this.view = view;
    }

    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        view.setPresenter(this);
        panel.setWidget(view);
    }
}
