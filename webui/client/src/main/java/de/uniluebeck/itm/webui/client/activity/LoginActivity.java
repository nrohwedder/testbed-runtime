package de.uniluebeck.itm.webui.client.activity;

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
import de.uniluebeck.itm.webui.client.ui.LoginView;
import de.uniluebeck.itm.webui.shared.NodeUrn;
import de.uniluebeck.itm.webui.shared.TestbedConfiguration;

import java.util.Iterator;
import java.util.List;

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

    private void bind() {
        testbedConfigurationSelectionModel.addSelectionChangeHandler(new Handler() {

            public void onSelectionChange(SelectionChangeEvent event) {
                currentTestbedConfiguration = testbedConfigurationSelectionModel.getSelectedObject();
                loginView.getDescriptionText().setText(currentTestbedConfiguration.getDescription());
                loadNodeUrns(currentTestbedConfiguration);
            }
        });
    }

    /**
     * Invoked by the ActivityManager to start a new Activity
     */
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        loginView = injector.getLoginView();
        loginView.setPresenter(this);
        loginView.getReloadEnabled().setEnabled(false);
        testbedConfigurationSelectionModel = new SingleSelectionModel<TestbedConfiguration>();
        loginView.setTestbedConfigurationSelectionModel(testbedConfigurationSelectionModel);

        bind();
        containerWidget.setWidget(loginView.asWidget());
        loadTestbedConfigurations();
    }

    private void loadTestbedConfigurations() {
        service.getTestbedConfigurations(new AsyncCallback<List<TestbedConfiguration>>() {

            public void onSuccess(List<TestbedConfiguration> configurations) {
                loginView.setConfigurations(configurations);
            }

            public void onFailure(Throwable throwable) {
            }
        });
    }

    private void loadNodeUrns(TestbedConfiguration configuration) {
        loginView.getReloadEnabled().setEnabled(false);
        service.getNetwork(configuration.getSessionmanagementEndointUrl(), new AsyncCallback<List<NodeUrn>>() {

            public void onSuccess(List<NodeUrn> nodes) {
                loginView.setNodeUrns(nodes);
                loginView.getReloadEnabled().setEnabled(true);
            }

            public void onFailure(Throwable throwable) {
                loginView.getReloadEnabled().setEnabled(true);
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

    public void reload() {
        loadNodeUrns(currentTestbedConfiguration);
    }

    public void showLoginDialog() {
        loginView.showLoginDialog("Login to " + currentTestbedConfiguration.getName());
    }

    public void hideLoginDialog() {
        loginView.hideLoginDialog();
    }

    public void submit() {
        loginView.getUsernameEnabled().setEnabled(false);
        loginView.getPasswordEnabled().setEnabled(false);

        final String endpointUrl = currentTestbedConfiguration.getSnaaEndpointUrl();
        final String username = loginView.getUsernameText().getText();
        final String password = loginView.getPasswordText().getText();

        final Iterator<String> iterator = currentTestbedConfiguration.getUrnPrefixList().iterator();

        final AsyncCallback<Void> callback = new AsyncCallback<Void>() {

            private String urn;
            private boolean error = false;

            public void onSuccess(Void result) {
                if (iterator.hasNext()) {
                    urn = iterator.next();
                    service.authenticate(endpointUrl, urn, username, password, this);
                } else {
                    if (!error) {
                        loginView.hideLoginDialog();
                    }
                    loginView.getUsernameEnabled().setEnabled(true);
                    loginView.getPasswordEnabled().setEnabled(true);
                }
            }

            public void onFailure(Throwable e) {
                error = true;
                loginView.addError(urn + " " + e.getMessage());
                onSuccess(null);
            }
        };
        callback.onSuccess(null);
    }
}
