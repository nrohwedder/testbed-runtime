package de.uniluebeck.itm.wiseui.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import de.uniluebeck.itm.wiseui.client.ui.ReservationView;
import de.uniluebeck.itm.wiseui.client.ui.ReservationView.Presenter;

public class ReservationActivity extends AbstractActivity implements Presenter {

    private ReservationView view;
    private EventBus eventBus;

    @Inject
    public ReservationActivity(final ReservationView view, final EventBus eventBus) {
        this.view = view;
        this.eventBus = eventBus;
    }

    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        view.setPresenter(this);
        panel.setWidget(view);
    }
}
