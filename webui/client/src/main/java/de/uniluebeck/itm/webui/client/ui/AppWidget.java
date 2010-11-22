package de.uniluebeck.itm.webui.client.ui;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public interface AppWidget extends IsWidget {

    AcceptsOneWidget getNavigationPanel();

    AcceptsOneWidget getContentPanel();
}
