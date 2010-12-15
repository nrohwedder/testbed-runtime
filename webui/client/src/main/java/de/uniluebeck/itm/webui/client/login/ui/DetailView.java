package de.uniluebeck.itm.webui.client.login.ui;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;

import de.uniluebeck.itm.webui.client.place.LoginPlace;
import de.uniluebeck.itm.webui.shared.wiseml.Coordinate;

public interface DetailView extends IsWidget {

    void setPresenter(Presenter presenter);
    
    void setDescriptionCoordinate(Coordinate coordinate);
    
    HasText getDescription();
    
    public interface Presenter {

        void setPlace(LoginPlace place);
    }
}
