package de.uniluebeck.itm.wiseui.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import de.uniluebeck.itm.wiseui.client.WiseUiGinjector;
import de.uniluebeck.itm.wiseui.client.ui.WiseMLNativeView;
import de.uniluebeck.itm.wiseui.client.ui.WiseMLNativeView.Presenter;

public class WiseMLNativeActivity extends AbstractActivity implements Presenter {

    private final WiseUiGinjector injector;

    private WiseMLNativeView view;

    @Inject
    public WiseMLNativeActivity(final WiseUiGinjector injector) {
        this.injector = injector;
    }

    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        view = injector.getWiseMLNativeView();
        view.setPresenter(this);

        panel.setWidget(view);
    }

}
