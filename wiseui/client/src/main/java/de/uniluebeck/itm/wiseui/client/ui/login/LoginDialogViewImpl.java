package de.uniluebeck.itm.wiseui.client.ui.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import de.uniluebeck.itm.wiseui.client.util.HasWidgetsDialogBox;

public class LoginDialogViewImpl extends HasWidgetsDialogBox implements LoginDialogView {

    private final static int SPACING = 5;

    private static LoginDialogViewImplUiBinder uiBinder = GWT.create(LoginDialogViewImplUiBinder.class);

    interface LoginDialogViewImplUiBinder extends UiBinder<Widget, LoginDialogViewImpl> {
    }

    @UiField
    VerticalPanel layoutPanel;

    @UiField
    HorizontalPanel buttonPanel;

    @UiField
    TextBox username;

    @UiField
    PasswordTextBox password;

    @UiField
    Button submit;

    @UiField
    Button cancel;

    @UiField
    VerticalPanel errorPanel;

    private Presenter presenter;

    @Inject
    public LoginDialogViewImpl() {
        uiBinder.createAndBindUi(this);

        layoutPanel.setCellHorizontalAlignment(buttonPanel, HasHorizontalAlignment.ALIGN_RIGHT);
        buttonPanel.setSpacing(SPACING);

        setModal(true);
        setGlassEnabled(true);
        setAnimationEnabled(true);
    }

    @UiFactory
    protected LoginDialogViewImpl createDialog() {
        return this;
    }

    @UiHandler("submit")
    public void onSubmit(final ClickEvent event) {
        presenter.submit();
    }

    @UiHandler("cancel")
    public void onCancel(final ClickEvent event) {
        presenter.cancel();
    }

    public void setPresenter(final Presenter presenter) {
        this.presenter = presenter;
    }

    public HasText getUsernameText() {
        return username;
    }

    public HasText getPasswordText() {
        return password;
    }

    public HasEnabled getUsernameEnabled() {
        return username;
    }

    public HasEnabled getPasswordEnabled() {
        return password;
    }

    public void show(final String title) {
        setText(title);
        center();
        show();
    }

    public void addError(String error) {
        errorPanel.add(new Label(error));
    }

    public void clearErrors() {
        errorPanel.clear();
    }
}
