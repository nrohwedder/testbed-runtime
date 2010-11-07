package de.uniluebeck.itm.webui.client.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabBar;

public class AppWidget extends DockLayoutPanel implements AcceptsOneWidget {

	private final TabBar tabBar = new TabBar();
	
	private final SimplePanel panel = new SimplePanel();
	
	private IsWidget currentWidget;
	
	public AppWidget() {
		super(Unit.PX);
		
		setWidth("100%");
		setHeight("100%");
		
		panel.setWidth("99%");
		panel.setHeight("99%");
		panel.setStyleName("default-border");
		
		tabBar.setWidth("100%");
		tabBar.addTab("Login");
		tabBar.selectTab(0);
		
		addNorth(tabBar, 24);
		add(panel);
	}
	
	@Override
	public void setWidget(IsWidget paramIsWidget) {	
		if (currentWidget != null) {
			panel.remove(currentWidget);
		}
		currentWidget = paramIsWidget;
		panel.add(paramIsWidget);
	}

}
