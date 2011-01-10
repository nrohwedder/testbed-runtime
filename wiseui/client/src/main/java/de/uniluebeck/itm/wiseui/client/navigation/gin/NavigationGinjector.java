package de.uniluebeck.itm.wiseui.client.navigation.gin;

import com.google.gwt.inject.client.Ginjector;

import de.uniluebeck.itm.wiseui.client.navigation.NavigationActivity;
import de.uniluebeck.itm.wiseui.client.navigation.view.NavigationView;

public interface NavigationGinjector extends Ginjector {

    NavigationView getNavigationView();
    
    NavigationActivity getNavigationActivity();
}
