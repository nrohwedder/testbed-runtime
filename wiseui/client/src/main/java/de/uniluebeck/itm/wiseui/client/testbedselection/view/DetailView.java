package de.uniluebeck.itm.wiseui.client.testbedselection.view;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;

import de.uniluebeck.itm.wiseui.client.testbedselection.TestbedSelectionPlace;
import de.uniluebeck.itm.wiseui.shared.wiseml.Coordinate;

public interface DetailView extends IsWidget {

    void setPresenter(Presenter presenter);

    void setDescriptionCoordinate(Coordinate coordinate);

    HasText getDescription();

    public interface Presenter {

        void setPlace(TestbedSelectionPlace place);
    }
}