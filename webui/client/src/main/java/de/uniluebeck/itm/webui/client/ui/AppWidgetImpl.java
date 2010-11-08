package de.uniluebeck.itm.webui.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class AppWidgetImpl extends Composite implements AppWidget {

    interface AppWidgetUiBinder extends UiBinder<Widget, AppWidgetImpl> {
    }
    
    private static AppWidgetUiBinder uiBinder = GWT.create(AppWidgetUiBinder.class);
    
    @UiField
    SimplePanel navigationPanel;
	
    @UiField
    SimplePanel contentPanel;
    
	public AppWidgetImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		
		setWidth("100%");
		setHeight("100%");
		
		contentPanel.setStyleName("default-border");
	}
	
	@Override
	public AcceptsOneWidget getNavigationPanel() {
		return navigationPanel;
	}
	
	@Override
	public AcceptsOneWidget getContentPanel() {
		return contentPanel;
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}
}
