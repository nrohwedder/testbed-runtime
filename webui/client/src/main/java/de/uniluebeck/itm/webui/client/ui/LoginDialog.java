package de.uniluebeck.itm.webui.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginDialog extends DialogBox {

    private final static int SPACING = 5;
    private final TextBox usernameTextBox = new TextBox();
    private final PasswordTextBox passwordTextBox = new PasswordTextBox();
    private final Button submitButton = new Button("Login");
    private final Button cancelButton = new Button("Cancel");
    private final VerticalPanel errorPanel = new VerticalPanel();
    private LoginView.Presenter presenter;

    public LoginDialog() {
        usernameTextBox.setWidth("200px");
        passwordTextBox.setWidth("200px");

        final VerticalPanel panel = new VerticalPanel();
        final FlexTable loginTable = new FlexTable();
        loginTable.setText(0, 0, "Username:");
        loginTable.setWidget(0, 1, usernameTextBox);
        loginTable.setText(1, 0, "Password:");
        loginTable.setWidget(1, 1, passwordTextBox);
        panel.add(loginTable);

        errorPanel.setStyleName("error");
        panel.add(errorPanel);

        submitButton.setWidth("50px");
        cancelButton.setWidth("50px");

        final HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.setSpacing(SPACING);
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        panel.add(buttonPanel);
        panel.setCellHorizontalAlignment(buttonPanel,
                HasHorizontalAlignment.ALIGN_RIGHT);

        setWidget(panel);
        setModal(true);
        setGlassEnabled(true);
        setAnimationEnabled(true);
    }

    private void bind() {
        submitButton.addClickHandler(new ClickHandler()  {

            public void onClick(final ClickEvent paramClickEvent) {
                presenter.submit();
            }
        });
        cancelButton.addClickHandler(new ClickHandler()  {

            public void onClick(final ClickEvent paramClickEvent) {
                presenter.hideLoginDialog();
            }
        });
    }

    public void setPresenter(final LoginView.Presenter presenter) {
        this.presenter = presenter;
        bind();
    }

    public TextBox getUsernameTextBox() {
        return usernameTextBox;
    }

    public TextBox getPasswordTextBox() {
        return passwordTextBox;
    }

    public Button getSubmitButton() {
        return submitButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public void addError(final String error) {
        errorPanel.add(new Label(error));
    }

    public void clearErrors() {
        errorPanel.clear();
    }
}