package de.uniluebeck.itm.wiseui.client.ui;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public interface WiseUiView extends IsWidget {

    AcceptsOneWidget getNavigationPanel();

    AcceptsOneWidget getContentPanel();
}
