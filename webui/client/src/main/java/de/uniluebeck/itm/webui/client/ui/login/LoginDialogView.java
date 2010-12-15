package de.uniluebeck.itm.webui.client.ui.login;

import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.view.client.HasData;

import de.uniluebeck.itm.webui.client.place.LoginPlace;
import de.uniluebeck.itm.webui.client.presenter.login.LoginDialogPresenter.UrnPrefixInfo;

public interface LoginDialogView {
    
    HasText getUsernameText();
    
    HasText getPasswordText();
    
    HasEnabled getUsernameEnabled();
    
    HasEnabled getPasswordEnabled();
    
    HasEnabled getSubmitEnabled();
    
    HasEnabled getCancelEnabled();
    
    HasData<UrnPrefixInfo> getUrnPrefixList();
    
    void setPresenter(Presenter presenter);
    
    void show(String title);
    
    void hide();
    
    public interface Presenter {

        void setPlace(LoginPlace place);
        
        void submit();
        
        void cancel();
    }
}
