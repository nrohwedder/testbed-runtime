package de.uniluebeck.itm.wiseui.client.failure.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import de.uniluebeck.itm.wiseui.client.failure.event.FailureEvent;
import de.uniluebeck.itm.wiseui.client.failure.view.FailureBoxView;

public class FailureBoxPresenter implements FailureBoxView.Presenter, FailureEvent.FailureHandler {

    private FailureBoxView view;

    private final EventBus eventBus;

    @Inject
    public FailureBoxPresenter(final EventBus eventBus, FailureBoxView view) {
        this.eventBus = eventBus;
        this.view = view;
        bind();
    }

    private void bind() {
        eventBus.addHandler(FailureEvent.TYPE, this);
    }

    public void handleFailure(FailureEvent event) {
        if (null == event) return;

        view.addMessage(event.getMessage());
        view.addStacktrace(event.getStacktrace());
        view.showBox();
    }
}
