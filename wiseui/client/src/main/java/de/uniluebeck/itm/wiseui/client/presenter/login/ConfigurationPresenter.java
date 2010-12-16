package de.uniluebeck.itm.wiseui.client.presenter.login;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;

import de.uniluebeck.itm.wiseui.api.TestbedConfigurationServiceAsync;
import de.uniluebeck.itm.wiseui.client.event.ConfigurationSelectedEvent;
import de.uniluebeck.itm.wiseui.client.place.LoginPlace;
import de.uniluebeck.itm.wiseui.client.ui.login.ConfigurationView;
import de.uniluebeck.itm.wiseui.client.ui.login.ConfigurationView.Presenter;
import de.uniluebeck.itm.wiseui.shared.TestbedConfiguration;

public class ConfigurationPresenter implements Presenter {

    private final EventBus eventBus;
    private final ConfigurationView view;
    private LoginPlace place;
    private final PlaceController placeController;
    private SingleSelectionModel<TestbedConfiguration> configurationSelectionModel;
    private List<TestbedConfiguration> configurations;
    private final TestbedConfigurationServiceAsync configurationService;

    @Inject
    public ConfigurationPresenter(final EventBus eventBus,
                                  final ConfigurationView view,
                                  final PlaceController placeController,
                                  final TestbedConfigurationServiceAsync configurationService) {
        this.eventBus = eventBus;
        this.view = view;
        this.placeController = placeController;
        this.configurationService = configurationService;

        // Init selection model
        configurationSelectionModel = new SingleSelectionModel<TestbedConfiguration>();
        view.setTestbedConfigurationSelectionModel(configurationSelectionModel);
    }

    public void setPlace(final LoginPlace place) {
        this.place = place;
        loadTestbedConfigurations();
        bind();
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

    private void loadTestbedConfigurations() {
        final AsyncCallback<List<TestbedConfiguration>> callback = new AsyncCallback<List<TestbedConfiguration>>() {

            public void onSuccess(final List<TestbedConfiguration> result) {
                configurations = result;
                view.setConfigurations(result);
                loadConfigurationSelectionFromPlace();
            }

            public void onFailure(final Throwable caught) {
                GWT.log(caught.getMessage());
            }
        };
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            public void execute() {
                configurationService.getConfigurations(callback);
            }
        });
    }

    public TestbedConfiguration getSelectedConfiguration() {
        final Integer selection = place.getSelection();
        return selection != null ? configurations.get(selection) : null;
    }

    private void loadConfigurationSelectionFromPlace() {
        final Integer selection = place.getSelection();
        GWT.log("Selection: " + selection);
        if (selection != null) {
            final TestbedConfiguration configuration = getSelectedConfiguration();
            if (configuration != null) {
                configurationSelectionModel.setSelected(configuration, true);
                eventBus.fireEvent(new ConfigurationSelectedEvent(configuration));
            }
        }
    }


}
