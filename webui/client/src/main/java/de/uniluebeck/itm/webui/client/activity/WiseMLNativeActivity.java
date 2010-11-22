package de.uniluebeck.itm.webui.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import de.uniluebeck.itm.webui.client.WebUiGinjector;
import de.uniluebeck.itm.webui.client.ui.WiseMLNativeView;
import de.uniluebeck.itm.webui.client.ui.WiseMLNativeView.Presenter;

public class WiseMLNativeActivity extends AbstractActivity implements Presenter {

    private final WebUiGinjector injector;

    private WiseMLNativeView view;

    @Inject
    public WiseMLNativeActivity(final WebUiGinjector injector) {
        this.injector = injector;
    }

    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        view = injector.getWiseMLNativeView();
        view.setPresenter(this);

        panel.setWidget(view);
    }

}
