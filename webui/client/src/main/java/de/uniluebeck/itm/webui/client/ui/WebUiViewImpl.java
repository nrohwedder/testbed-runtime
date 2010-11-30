package de.uniluebeck.itm.webui.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class WebUiViewImpl extends Composite implements WebUiView {

    interface WebUiViewImplUiBinder extends UiBinder<Widget, WebUiViewImpl> {
    }
    private static WebUiViewImplUiBinder uiBinder = GWT.create(WebUiViewImplUiBinder.class);
    @UiField
    SimplePanel navigationPanel;
    @UiField
    SimplePanel contentPanel;

    public WebUiViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        setWidth("100%");
        setHeight("100%");

        contentPanel.setStyleName("default-border");
    }

    public AcceptsOneWidget getNavigationPanel() {
        return navigationPanel;
    }

    public AcceptsOneWidget getContentPanel() {
        return contentPanel;
    }

    @Override
    public Widget asWidget() {
        return this;
    }
}
