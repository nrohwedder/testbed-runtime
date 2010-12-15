package de.uniluebeck.itm.webui.client.login.ui;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.IsWidget;

import de.uniluebeck.itm.webui.client.place.LoginPlace;

public interface LoginView extends IsWidget {

    void setPresenter(Presenter presenter);
    
    AcceptsOneWidget getConfigurationContainer();
    
    AcceptsOneWidget getDetailContainer();
    
    AcceptsOneWidget getNetworkContainer();
    
    HasEnabled getLoginEnabled();

    HasEnabled getReloadEnabled();
    
    public interface Presenter {
        
        void reload();
        
        void showLoginDialog();
        
        void setPlace(LoginPlace place);
    }
}
