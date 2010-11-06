package de.uniluebeck.itm.webui.client.activity;

import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;

import de.uniluebeck.itm.webui.api.TestbedServiceAsync;
import de.uniluebeck.itm.webui.client.WebUiGinjector;
import de.uniluebeck.itm.webui.client.place.LoginPlace;
import de.uniluebeck.itm.webui.client.ui.LoginView;
import de.uniluebeck.itm.webui.shared.NodeUrn;
import de.uniluebeck.itm.webui.shared.TestbedConfiguration;

public class LoginActivity extends AbstractActivity implements LoginView.Presenter {

    private final WebUiGinjector injector;

    private final TestbedServiceAsync service;
    
    private LoginView loginView;
    
    private SingleSelectionModel<TestbedConfiguration> testbedConfigurationSelectionModel;
    
    private TestbedConfiguration currentTestbedConfiguration;
    
    @Inject
    public LoginActivity(WebUiGinjector injector, TestbedServiceAsync service) {
        this.injector = injector;
        this.service = service;
    }
    
    public LoginActivity withPlace(LoginPlace place) {
    	return this;
    }
    
    private void bind() {
    	testbedConfigurationSelectionModel.addSelectionChangeHandler(new Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				currentTestbedConfiguration = testbedConfigurationSelectionModel.getSelectedObject();
				System.out.println(currentTestbedConfiguration.getName());
				loginView.getDescriptionField().setText(currentTestbedConfiguration.getDescription());
				loadNodeUrns(currentTestbedConfiguration);
			}
		});
    }

    /**
     * Invoked by the ActivityManager to start a new Activity
     */
    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        loginView = injector.getLoginView();
        loginView.setPresenter(this);
        testbedConfigurationSelectionModel = new SingleSelectionModel<TestbedConfiguration>();
        loginView.setTestbedConfigurationSelectionModel(testbedConfigurationSelectionModel);
        
        bind();
        containerWidget.setWidget(loginView.asWidget());
        loadTestbedConfigurations();
    }
    
    private void loadTestbedConfigurations() {
    	service.getTestbedConfigurations(new AsyncCallback<List<TestbedConfiguration>>() {
			@Override
			public void onSuccess(List<TestbedConfiguration> configurations) {
				loginView.setConfigurations(configurations);
			}
			
			@Override
			public void onFailure(Throwable throwable) {
				throwable.printStackTrace();
			}
		});
    }
    
    private void loadNodeUrns(TestbedConfiguration configuration) {
    	service.getNetwork(configuration.getSessionmanagementEndointUrl(), new AsyncCallback<List<NodeUrn>>() {
    		@Override
    		public void onSuccess(List<NodeUrn> nodes) {
    			loginView.setNodeUrns(nodes);
    		}
    		
    		@Override
    		public void onFailure(Throwable throwable) {
    			throwable.printStackTrace();
    		}
		});
    }

    /**
     * Ask user before stopping this activity
     */
    @Override
    public String mayStop() {
        return "Please hold on. This activity is stopping.";
    }

	@Override
	public void reload() {
		if (currentTestbedConfiguration != null) {
			loadNodeUrns(currentTestbedConfiguration);
		}
	}

	@Override
	public void login() {
		
	}
}
