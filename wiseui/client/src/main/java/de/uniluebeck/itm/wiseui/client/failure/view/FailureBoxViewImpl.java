package de.uniluebeck.itm.wiseui.client.failure.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class FailureBoxViewImpl implements FailureBoxView {

    private static FailureBoxViewImplUiBinder uiBinder = GWT.create(FailureBoxViewImplUiBinder.class);

    interface FailureBoxViewImplUiBinder extends UiBinder<Widget, FailureBoxViewImpl> {
    }

    @UiField
    DialogBox dialogBox;
    @UiField
    Button closeButton;
    @UiField
    Label messageLabel;
    @UiField
    Label stacktraceLabel;

    private Presenter presenter;

    public FailureBoxViewImpl() {
        uiBinder.createAndBindUi(this);
    }

    public void addMessage(final String message) {
        messageLabel.setText(message.trim());
    }

    public void addStacktrace(final String stacktrace) {
        stacktraceLabel.setText(stacktrace.trim());
    }

    public void showBox() {
        dialogBox.center();
        dialogBox.show();
    }

    public void hideBox() {
        dialogBox.hide();
    }

    public void setPresenter(final Presenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("closeButton")
    void handleCloseClick(final ClickEvent event) {
        dialogBox.hide();
    }
}
