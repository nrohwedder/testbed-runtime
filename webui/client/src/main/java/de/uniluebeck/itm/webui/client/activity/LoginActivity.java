package de.uniluebeck.itm.webui.client.activity;

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

import java.util.Iterator;
import java.util.List;

public class LoginActivity extends AbstractActivity implements LoginView.Presenter {

    private final WebUiGinjector injector;

    private final TestbedServiceAsync service;

    private LoginView view;

    private SingleSelectionModel<TestbedConfiguration> configurationSelectionModel;

    private List<TestbedConfiguration> configurations;

    private LoginPlace place;

    @Inject
    public LoginActivity(final WebUiGinjector injector, final TestbedServiceAsync service) {
        this.injector = injector;
        this.service = service;
    }

    public void setPlace(final LoginPlace place) {
        this.place = place;
    }

    public TestbedConfiguration getSelectedConfiguration() {
        final Integer selection = place.getSelection();
        return selection != null ? configurations.get(selection) : null;
    }

    private void bind() {
        configurationSelectionModel.addSelectionChangeHandler(new Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                onConfigurationSelectionChange(event);
            }
        });
    }

    private void onConfigurationSelectionChange(final SelectionChangeEvent event) {
        final TestbedConfiguration configuration = configurationSelectionModel
                .getSelectedObject();
        final Integer index = configurations.indexOf(configuration);
        if (!index.equals(place.getSelection())) {
            injector.getPlaceController().goTo(new LoginPlace(index));
        }
    }

    /**
     * Invoked by the ActivityManager to start a new Activity
     */
    public void start(final AcceptsOneWidget containerWidget, final EventBus eventBus) {
        view = injector.getLoginView();
        view.setPresenter(this);
        view.getLoginEnabled().setEnabled(false);
        view.getReloadEnabled().setEnabled(false);

        // Init selection model
        configurationSelectionModel = new SingleSelectionModel<TestbedConfiguration>();
        view.setTestbedConfigurationSelectionModel(configurationSelectionModel);

        bind();
        containerWidget.setWidget(view.asWidget());
        loadTestbedConfigurations();
    }

    private void loadTestbedConfigurations() {
        final AsyncCallback<List<TestbedConfiguration>> callback = new AsyncCallback<List<TestbedConfiguration>>() {
            public void onSuccess(List<TestbedConfiguration> result) {
                configurations = result;
                view.setConfigurations(result);
                loadConfigurationSelectionFromPlace();
            }

            public void onFailure(final Throwable throwable) {
                throwable.printStackTrace();
            }
        };
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            public void execute() {
                service.getTestbedConfigurations(callback);
            }
        });
    }

    private void loadConfigurationSelectionFromPlace() {
        final Integer selection = place.getSelection();
        GWT.log("Selection: " + selection);
        if (selection != null) {
            TestbedConfiguration configuration = getSelectedConfiguration();
            if (configurations.size() > selection) {
                configurationSelectionModel.setSelected(configuration, true);
            }
            view.getDescriptionText().setText(configuration.getDescription());
            view.getLoginEnabled().setEnabled(true);
            loadNetwork(configuration);
        }
    }

    private void loadNetwork(final TestbedConfiguration configuration) {
        view.getReloadEnabled().setEnabled(false);
        AsyncCallback<List<NodeUrn>> callback = new AsyncCallback<List<NodeUrn>>() {
            public void onSuccess(List<NodeUrn> nodes) {
                view.setNodeUrns(nodes);
                view.getReloadEnabled().setEnabled(true);
            }

            public void onFailure(final Throwable throwable) {
                throwable.printStackTrace();
                view.getReloadEnabled().setEnabled(true);
            }
        };
        service.getNetwork(configuration.getSessionmanagementEndointUrl(),
                callback);
    }

    public void reload() {
        loadNetwork(getSelectedConfiguration());
    }

    public void showLoginDialog() {
        view.showLoginDialog("Login to " + getSelectedConfiguration().getName());
    }

    public void hideLoginDialog() {
        view.hideLoginDialog();
    }

    // TODO: Review this code.
    public void submit() {
        view.getUsernameEnabled().setEnabled(false);
        view.getPasswordEnabled().setEnabled(false);

        final String endpointUrl = configurations.get(place.getSelection())
                .getSnaaEndpointUrl();
        final String username = view.getUsernameText().getText();
        final String password = view.getPasswordText().getText();

        final Iterator<String> iterator = configurations
                .get(place.getSelection()).getUrnPrefixList().iterator();

        final AsyncCallback<Void> callback = new AsyncCallback<Void>() {

            private String urn;

            private boolean error = false;

            public void onSuccess(final Void result) {
                if (iterator.hasNext()) {
                    urn = iterator.next();
                    service.authenticate(endpointUrl, urn, username, password,
                            this);
                } else {
                    if (!error) {
                        view.hideLoginDialog();
                    }
                    view.getUsernameEnabled().setEnabled(true);
                    view.getPasswordEnabled().setEnabled(true);
                }
            }

            public void onFailure(final Throwable throwable) {
                error = true;
                view.addError(urn + " " + throwable.getMessage());
                onSuccess(null);
            }
        };
        callback.onSuccess(null);
    }
}
