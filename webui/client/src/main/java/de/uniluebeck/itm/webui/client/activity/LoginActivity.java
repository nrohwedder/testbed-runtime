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

    private SingleSelectionModel<TestbedConfiguration> testbedConfigurationSelectionModel;

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

    private void bind() {
        testbedConfigurationSelectionModel.addSelectionChangeHandler(new Handler() {
            public void onSelectionChange(final SelectionChangeEvent event) {
                final TestbedConfiguration configuration = testbedConfigurationSelectionModel.getSelectedObject();
                final Integer index = configurations.indexOf(configuration);
                if (!place.getSelection().equals(index)) {
                    injector.getPlaceController().goTo(new LoginPlace(index));
                }
            }
        });
    }

    /**
     * Invoked by the ActivityManager to start a new Activity
     */
    public void start(final AcceptsOneWidget containerWidget, final EventBus eventBus) {
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
            public void onSuccess(final List<TestbedConfiguration> configurations) {
                LoginActivity.this.configurations = configurations;
                view.setConfigurations(configurations);
                loadSelection();
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

    private void loadSelection() {
        final Integer selection = place.getSelection();
        GWT.log("Selection: " + selection);
        final boolean isSelected = selection != null;
        if (isSelected) {
            view.getLoginEnabled().setEnabled(isSelected);
            view.getDescriptionText().setText(configurations.get(selection).getDescription());
            if (configurations.size() > selection) {
                testbedConfigurationSelectionModel.setSelected(configurations.get(selection), true);
            }
            loadNodeUrns(configurations.get(selection));
        }
    }

    private void loadNodeUrns(final TestbedConfiguration configuration) {
        view.getReloadEnabled().setEnabled(false);
        service.getNetwork(configuration.getSessionmanagementEndointUrl(), new AsyncCallback<List<NodeUrn>>() {
            public void onSuccess(final List<NodeUrn> nodes) {
                view.setNodeUrns(nodes);
                view.getReloadEnabled().setEnabled(true);
            }

            public void onFailure(final Throwable throwable) {
                throwable.printStackTrace();
                view.getReloadEnabled().setEnabled(true);
            }
        });
    }

    public void reload() {
        loadNodeUrns(configurations.get(place.getSelection()));
    }

    public void showLoginDialog() {
        view.showLoginDialog("Login to " + configurations.get(place.getSelection()).getName());
    }

    public void hideLoginDialog() {
        view.hideLoginDialog();
    }

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

            public void onSuccess(final Void result) {
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

            public void onFailure(final Throwable throwable) {
                error = true;
                view.addError(urn + " " + throwable.getMessage());
                onSuccess(null);
            }
        };
        callback.onSuccess(null);
    }
}
