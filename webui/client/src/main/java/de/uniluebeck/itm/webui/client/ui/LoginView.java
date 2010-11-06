package de.uniluebeck.itm.webui.client.ui;

import java.util.List;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.SelectionModel;

import de.uniluebeck.itm.webui.shared.NodeUrn;
import de.uniluebeck.itm.webui.shared.TestbedConfiguration;

/**
 * View interface. Extends IsWidget so a view impl can easily provide
 * its container widget.
 *
 * @author drfibonacci
 */
public interface LoginView extends IsWidget {

	HasText getDescriptionField();
	
	void setConfigurations(List<TestbedConfiguration> configurations);
	
	void setNodeUrns(List<NodeUrn> nodes);
	
    void setPresenter(Presenter listener);
    
    void setTestbedConfigurationSelectionModel(SelectionModel<TestbedConfiguration> selectionModel);

    public interface Presenter {

    	void reload();
    	
    	void login();
    }
}
