package de.uniluebeck.itm.webui.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.Widget;

public class NavigationViewImpl extends Composite implements NavigationView {

    interface NavigationViewImplUiBinder extends UiBinder<Widget, NavigationViewImpl> {
    }

    private static NavigationViewImplUiBinder uiBinder = GWT.create(NavigationViewImplUiBinder.class);

    @UiField
    TabBar navigationBar;

    private Presenter presenter;

    public NavigationViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        navigationBar.setWidth("100%");
        navigationBar.addTab("Login");
        navigationBar.addTab("Reservation");
        navigationBar.selectTab(0);
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

}
