package de.uniluebeck.itm.webui.client.ui.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class LoginViewImpl extends Composite implements LoginView {

    private static LoginViewImplUiBinder uiBinder = GWT.create(LoginViewImplUiBinder.class);

    interface LoginViewImplUiBinder extends UiBinder<Widget, LoginViewImpl> {
    }
    
    @UiField
    SimplePanel configurationContainer;
    @UiField
    SimplePanel detailContainer;
    @UiField
    SimplePanel networkContainer;
    @UiField
    Button reloadButton;
    @UiField
    Button loginButton;
    private Presenter presenter;

    @Inject
    public LoginViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }
    
    @UiHandler("reloadButton")
    public void handleReloadClick(final ClickEvent event) {
        presenter.reload();
    }

    @UiHandler("loginButton")
    public void handleLoginClick(final ClickEvent event) {
        presenter.showLoginDialog();
    }

    public void setPresenter(final Presenter presenter) {
        this.presenter = presenter;
    }

    public AcceptsOneWidget getConfigurationContainer() {
        return configurationContainer;
    }

    public AcceptsOneWidget getDetailContainer() {
        return detailContainer;
    }

    public AcceptsOneWidget getNetworkContainer() {
        return networkContainer;
    }

    public HasEnabled getLoginEnabled() {
        return loginButton;
    }

    public HasEnabled getReloadEnabled() {
        return reloadButton;
    }
}
