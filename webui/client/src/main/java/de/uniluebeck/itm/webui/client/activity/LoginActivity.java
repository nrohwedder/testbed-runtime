package de.uniluebeck.itm.webui.client.activity;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
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
    
    private LoginView view;
    
    private SingleSelectionModel<TestbedConfiguration> testbedConfigurationSelectionModel;
    
    private List<TestbedConfiguration> configurations;
    
    private LoginPlace place;
    
    @Inject
    public LoginActivity(WebUiGinjector injector, TestbedServiceAsync service) {
        this.injector = injector;
        this.service = service;
    }
    
    public void setPlace(LoginPlace place) {
    	this.place = place;
    }
    
    private void bind() {
    	testbedConfigurationSelectionModel.addSelectionChangeHandler(new Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				TestbedConfiguration configuration = testbedConfigurationSelectionModel.getSelectedObject();
				Integer index = configurations.indexOf(configuration);
				if (place.getSelection() != index) {
					injector.getPlaceController().goTo(new LoginPlace(index));
				}
			}
		});
    }

    /**
     * Invoked by the ActivityManager to start a new Activity
     */
    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {    	
        view = injector.getLoginView();
        view.setPresenter(this);
        view.getLoginEnabled().setEnabled(false);
        view.getReloadEnabled().setEnabled(false);
        testbedConfigurationSelectionModel = new SingleSelectionModel<TestbedConfiguration>();
        view.setTestbedConfigurationSelectionModel(testbedConfigurationSelectionModel);
        
        bind();
        containerWidget.setWidget(view.asWidget());
        loadTestbedConfigurations();
    }
    
    private void loadTestbedConfigurations() {
    	final AsyncCallback<List<TestbedConfiguration>> callback = new AsyncCallback<List<TestbedConfiguration>>() {
			@Override
			public void onSuccess(List<TestbedConfiguration> configurations) {
				LoginActivity.this.configurations = configurations;
				view.setConfigurations(configurations);
				loadSelection();
			}
			
			@Override
			public void onFailure(Throwable throwable) {
				throwable.printStackTrace();
			}
		};
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				service.getTestbedConfigurations(callback);
			}
		});
    }
    
    private void loadSelection() {
		Integer selection = place.getSelection();
		GWT.log("Selection: " + selection);
		if (selection != null) {
			view.getLoginEnabled().setEnabled(selection != null);
			view.getDescriptionText().setText(configurations.get(selection).getDescription());
			if (configurations.size() > selection) {
				testbedConfigurationSelectionModel.setSelected(configurations.get(selection), true);
			}
			loadNodeUrns(configurations.get(selection));
		}
    }
    
    private void loadNodeUrns(TestbedConfiguration configuration) {
    	view.getReloadEnabled().setEnabled(false);
    	service.getNetwork(configuration.getSessionmanagementEndointUrl(), new AsyncCallback<List<NodeUrn>>() {
    		@Override
    		public void onSuccess(List<NodeUrn> nodes) {
    			view.setNodeUrns(nodes);
    			view.getReloadEnabled().setEnabled(true);
    		}
    		
    		@Override
    		public void onFailure(Throwable throwable) {
    			throwable.printStackTrace();
    			view.getReloadEnabled().setEnabled(true);
    		}
		});
    }

	@Override
	public void reload() {
		loadNodeUrns(configurations.get(place.getSelection()));
	}

	@Override
	public void showLoginDialog() {
		view.showLoginDialog("Login to " + configurations.get(place.getSelection()).getName());
	}
	
	@Override
	public void hideLoginDialog() {
		view.hideLoginDialog();
	}

	@Override
	public void submit() {
		view.getUsernameEnabled().setEnabled(false);
		view.getPasswordEnabled().setEnabled(false);
		
		final String endpointUrl = configurations.get(place.getSelection()).getSnaaEndpointUrl();
		final String username = view.getUsernameText().getText();
		final String password = view.getPasswordText().getText();
		
		final Iterator<String> iterator = configurations.get(place.getSelection()).getUrnPrefixList().iterator();
		
		final AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			
			private String urn;
			
			private boolean error = false;
			
			@Override
			public void onSuccess(Void result) {
				if (iterator.hasNext()) {
					urn = iterator.next();
					service.authenticate(endpointUrl, urn, username, password, this);
				} else {
					if (!error) {
						view.hideLoginDialog();
					}
					view.getUsernameEnabled().setEnabled(true);
					view.getPasswordEnabled().setEnabled(true);
				}
			}
			
			@Override
			public void onFailure(Throwable e) {
				error = true;
				view.addError(urn + " " + e.getMessage());
				onSuccess(null);
			}
		};
		callback.onSuccess(null);
	}
}
