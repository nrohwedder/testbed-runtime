package de.uniluebeck.itm.webui.client.ui;

import java.util.List;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.SelectionModel;

import de.uniluebeck.itm.webui.shared.TestbedConfiguration;
import de.uniluebeck.itm.webui.shared.wiseml.Node;

public interface LoginView extends IsWidget {

    HasText getDescriptionText();

    HasText getUsernameText();

    HasText getPasswordText();
    
    MapWidget getMapWidget();

    HasEnabled getUsernameEnabled();

    HasEnabled getPasswordEnabled();

    HasEnabled getLoginEnabled();

    HasEnabled getSubmitEnabled();

    HasEnabled getReloadEnabled();

    void setConfigurations(List<TestbedConfiguration> configurations);

    void setNodes(List<Node> nodes);

    void setPresenter(Presenter listener);

    void setTestbedConfigurationSelectionModel(
            SelectionModel<TestbedConfiguration> selectionModel);

    void showLoginDialog(String title);

    void hideLoginDialog();

    void addError(String error);

    void clearErrors();

    void disableLoginForm();

    public interface Presenter {

        void reload();

        void showLoginDialog();

        void hideLoginDialog();

        void submit();
    }
}
