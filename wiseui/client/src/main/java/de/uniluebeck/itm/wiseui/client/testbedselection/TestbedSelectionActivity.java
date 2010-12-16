package de.uniluebeck.itm.wiseui.client.testbedselection;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import de.uniluebeck.itm.wiseui.api.SessionManagementServiceAsync;
import de.uniluebeck.itm.wiseui.client.WiseUiGinjector;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ConfigurationSelectedEvent;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.ConfigurationSelectedHandler;
import de.uniluebeck.itm.wiseui.client.testbedselection.event.WisemlLoadedEvent;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.ConfigurationPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.DetailPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.LoginDialogPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.TestbedSelectionPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.presenter.NetworkPresenter;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.*;
import de.uniluebeck.itm.wiseui.client.testbedselection.view.TestbedSelectionView;
import de.uniluebeck.itm.wiseui.shared.TestbedConfiguration;
import de.uniluebeck.itm.wiseui.shared.wiseml.Wiseml;

public class TestbedSelectionActivity extends AbstractActivity implements ConfigurationSelectedHandler {

    private final SessionManagementServiceAsync sessionManagementService;
    private TestbedSelectionPlace place;
    private WiseUiGinjector injector;
    private EventBus eventBus;

    @Inject
    public TestbedSelectionActivity(final WiseUiGinjector injector,
                                    final SessionManagementServiceAsync sessionManagementService) {
        this.injector = injector;
        this.sessionManagementService = sessionManagementService;
    }

    public void setPlace(final TestbedSelectionPlace place) {
        this.place = place;
    }

    /**
     * Invoked by the ActivityManager to start a new {@link com.google.gwt.activity.shared.Activity}.
     */
    public void start(final AcceptsOneWidget containerWidget, final EventBus eventBus) {
        this.eventBus = eventBus;
        initTestbedSelectionPart(containerWidget);
        initLoginDialogPart();
        bind();
    }

    private void bind() {
        eventBus.addHandler(ConfigurationSelectedEvent.TYPE, this);
    }

    private void initTestbedSelectionPart(final AcceptsOneWidget container) {
        GWT.log("Init Testbed Selection Part");
        final TestbedSelectionPresenter testbedSelectionPresenter = injector.getTestbedSelectionPresenter();
        testbedSelectionPresenter.setPlace(place);
        final TestbedSelectionView testbedSelectionView = injector.getTestbedSelectionView();
        testbedSelectionView.setPresenter(testbedSelectionPresenter);
        initConfigurationPart(testbedSelectionView);
        initDetailPart(testbedSelectionView);
        initNetworkPart(testbedSelectionView);
        container.setWidget(testbedSelectionView.asWidget());
    }

    private void initConfigurationPart(final TestbedSelectionView testbedSelectionView) {
        GWT.log("Init Testbed Configuration Part");
        final ConfigurationPresenter configurationPresenter = injector.getConfigurationPresenter();
        configurationPresenter.setPlace(place);
        final ConfigurationView configurationView = injector.getConfigurationView();
        configurationView.setPresenter(configurationPresenter);
        testbedSelectionView.getConfigurationContainer().setWidget(configurationView);
    }

    private void initDetailPart(final TestbedSelectionView testbedSelectionView) {
        GWT.log("Init Testbed Detail Part");
        final DetailPresenter detailPresenter = injector.getDetailPresenter();
        detailPresenter.setPlace(place);
        final DetailView detailView = injector.getDetailView();
        detailView.setPresenter(detailPresenter);
        testbedSelectionView.getDetailContainer().setWidget(detailView);
    }

    private void initNetworkPart(final TestbedSelectionView testbedSelectionView) {
        GWT.log("Init Testbed Network Part");
        final NetworkPresenter networkPresenter = injector.getNetworkPresenter();
        networkPresenter.setPlace(place);
        final NetworkView networkView = injector.getNetworkView();
        networkView.setPresenter(networkPresenter);
        testbedSelectionView.getNetworkContainer().setWidget(networkView);
    }

    private void initLoginDialogPart() {
        GWT.log("Init Login Dialog Part");
        final LoginDialogPresenter loginDialogPresenter = injector.getLoginDialogPresenter();
        loginDialogPresenter.setPlace(place);
        final LoginDialogView loginDialogView = injector.getLoginDialogView();
        loginDialogView.setPresenter(loginDialogPresenter);
    }

    public void onTestbedConfigurationSelected(final ConfigurationSelectedEvent event) {
        final TestbedConfiguration configuration = event.getConfiguration();
        final AsyncCallback<Wiseml> callback = new AsyncCallback<Wiseml>() {

            public void onSuccess(final Wiseml result) {
                eventBus.fireEvent(new WisemlLoadedEvent(result));
            }

            public void onFailure(final Throwable caught) {

            }
        };
        final String url = configuration.getSessionmanagementEndointUrl();
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            public void execute() {
                sessionManagementService.getWiseml(url, callback);
            }
        });
    }
}
