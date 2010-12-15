package de.uniluebeck.itm.webui.client.login.ui;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;

import de.uniluebeck.itm.webui.client.place.LoginPlace;
import de.uniluebeck.itm.webui.shared.wiseml.Node;

public interface NetworkView extends IsWidget {

    void setPresenter(Presenter presenter);
    
    void setNodes(final List<Node> nodes);
    
    public interface Presenter {

        void setPlace(LoginPlace place);
    }
}
