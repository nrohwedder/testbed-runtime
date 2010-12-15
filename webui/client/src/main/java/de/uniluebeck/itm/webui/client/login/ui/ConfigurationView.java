package de.uniluebeck.itm.webui.client.login.ui;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.SelectionModel;

import de.uniluebeck.itm.webui.client.place.LoginPlace;
import de.uniluebeck.itm.webui.shared.TestbedConfiguration;

public interface ConfigurationView extends IsWidget {

    void setPresenter(Presenter presenter);
    
    void setTestbedConfigurationSelectionModel(SelectionModel<TestbedConfiguration> selectionModel);
    
    void setConfigurations(List<TestbedConfiguration> configurations);
    
    public interface Presenter {

        void setPlace(LoginPlace place);
    }
}
