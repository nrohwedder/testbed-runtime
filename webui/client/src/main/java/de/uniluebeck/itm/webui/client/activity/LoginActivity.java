package de.uniluebeck.itm.webui.client.activity;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;

import de.uniluebeck.itm.webui.api.SNAAServiceAsync;
import de.uniluebeck.itm.webui.api.SessionManagementServiceAsync;
import de.uniluebeck.itm.webui.api.TestbedConfigurationServiceAsync;
import de.uniluebeck.itm.webui.client.place.LoginPlace;
import de.uniluebeck.itm.webui.client.ui.LoginView;
import de.uniluebeck.itm.webui.shared.TestbedConfiguration;
import de.uniluebeck.itm.webui.shared.wiseml.SecretAuthenticationKey;
import de.uniluebeck.itm.webui.shared.wiseml.Setup;
import de.uniluebeck.itm.webui.shared.wiseml.Wiseml;

public class LoginActivity extends AbstractActivity implements
        LoginView.Presenter {

    private final SNAAServiceAsync authenticationService;
    private final TestbedConfigurationServiceAsync configurationService;
    private final SessionManagementServiceAsync sessionManagementService;
    private PlaceController placeController;
    private LoginView view;
    private LoginPlace place;
    private SingleSelectionModel<TestbedConfiguration> configurationSelectionModel;
    private List<TestbedConfiguration> configurations;

    @Inject
    public LoginActivity(final LoginView view,
            final PlaceController placeController,
            final TestbedConfigurationServiceAsync configurationService,
            final SNAAServiceAsync authenticationService,
            final SessionManagementServiceAsync sessionManagementService) {
        this.view = view;
        this.placeController = placeController;
        this.configurationService = configurationService;
        this.authenticationService = authenticationService;
        this.sessionManagementService = sessionManagementService;
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

            public void onSelectionChange(final SelectionChangeEvent event) {
                onConfigurationSelectionChange(event);
            }
        });
    }

    private void onConfigurationSelectionChange(final SelectionChangeEvent event) {
        final TestbedConfiguration configuration = configurationSelectionModel.getSelectedObject();
        final Integer index = configurations.indexOf(configuration);
        if (!index.equals(place.getSelection())) {
            placeController.goTo(new LoginPlace(index));
        }
    }

    /**
     * Invoked by the ActivityManager to start a new {@link com.google.gwt.shared.Activity}.
     */
    public void start(final AcceptsOneWidget containerWidget,
            final EventBus eventBus) {
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

            public void onSuccess(final List<TestbedConfiguration> result) {
                configurations = result;
                view.setConfigurations(result);
                loadConfigurationSelectionFromPlace();
            }

            public void onFailure(final Throwable caught) {
                view.addError(caught.getMessage());
                GWT.log(caught.getMessage());
            }
        };
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            public void execute() {
                configurationService.getConfigurations(callback);
            }
        });
    }

    private void loadConfigurationSelectionFromPlace() {
        final Integer selection = place.getSelection();
        GWT.log("Selection: " + selection);
        if (selection != null) {
            final TestbedConfiguration configuration = getSelectedConfiguration();
            if (configurations.size() > selection) {
                configurationSelectionModel.setSelected(configuration, true);
            }
            //view.getDescriptionText().setText(configuration.getDescription());
            view.getLoginEnabled().setEnabled(true);
            loadWiseml(configuration);
        }
    }

    private void loadWiseml(final TestbedConfiguration configuration) {
        view.getDescriptionText().setText("Loading Description...");
        view.getReloadEnabled().setEnabled(false);
        final AsyncCallback<Wiseml> callback = new AsyncCallback<Wiseml>() {

            public void onSuccess(final Wiseml result) {
                afterWisemlLoaded(result);
            }

            public void onFailure(final Throwable caught) {
                view.getReloadEnabled().setEnabled(true);
            }
        };
        final String url = configuration.getSessionmanagementEndointUrl();
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            public void execute() {
                sessionManagementService.getWiseml(url, callback);
            }
        });

    }
    
    private void afterWisemlLoaded(final Wiseml wiseml) {
        final Setup setup = wiseml.getSetup();
        view.setDescriptionCoordinate(setup.getOrigin());
        view.getDescriptionText().setText(setup.getDescription());
        view.setNodes(setup.getNode());
        view.getReloadEnabled().setEnabled(true);
    }

    public void reload() {
        loadWiseml(getSelectedConfiguration());
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

        final String endpointUrl = configurations.get(place.getSelection()).getSnaaEndpointUrl();
        final String username = view.getUsernameText().getText();
        final String password = view.getPasswordText().getText();

        final Iterator<String> iterator = configurations.get(place.getSelection()).getUrnPrefixList().iterator();

        final AsyncCallback<SecretAuthenticationKey> callback = new AsyncCallback<SecretAuthenticationKey>() {

            private String urn;
            private boolean error = false;

            public void onSuccess(final SecretAuthenticationKey result) {
                if (iterator.hasNext()) {
                    urn = iterator.next();
                    authenticationService.authenticate(endpointUrl, urn, username, password,
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
