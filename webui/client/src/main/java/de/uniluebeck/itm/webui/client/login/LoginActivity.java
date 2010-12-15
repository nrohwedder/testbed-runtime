package de.uniluebeck.itm.webui.client.login;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import de.uniluebeck.itm.webui.api.SessionManagementServiceAsync;
import de.uniluebeck.itm.webui.client.WebUiGinjector;
import de.uniluebeck.itm.webui.client.event.ConfigurationSelectedEvent;
import de.uniluebeck.itm.webui.client.event.ConfigurationSelectedHandler;
import de.uniluebeck.itm.webui.client.event.WisemlLoadedEvent;
import de.uniluebeck.itm.webui.client.login.presenter.ConfigurationPresenter;
import de.uniluebeck.itm.webui.client.login.presenter.DetailPresenter;
import de.uniluebeck.itm.webui.client.login.presenter.LoginDialogPresenter;
import de.uniluebeck.itm.webui.client.login.presenter.LoginPresenter;
import de.uniluebeck.itm.webui.client.login.presenter.NetworkPresenter;
import de.uniluebeck.itm.webui.client.login.ui.ConfigurationView;
import de.uniluebeck.itm.webui.client.login.ui.DetailView;
import de.uniluebeck.itm.webui.client.login.ui.LoginDialogView;
import de.uniluebeck.itm.webui.client.login.ui.LoginView;
import de.uniluebeck.itm.webui.client.login.ui.NetworkView;
import de.uniluebeck.itm.webui.client.place.LoginPlace;
import de.uniluebeck.itm.webui.shared.TestbedConfiguration;
import de.uniluebeck.itm.webui.shared.wiseml.Wiseml;

public class LoginActivity extends AbstractActivity implements ConfigurationSelectedHandler {

    private final SessionManagementServiceAsync sessionManagementService;
    private LoginPlace place;
    private WebUiGinjector injector;
    private EventBus eventBus;

    @Inject
    public LoginActivity(final WebUiGinjector injector,
            final SessionManagementServiceAsync sessionManagementService) {
        this.injector = injector;
        this.sessionManagementService = sessionManagementService;
    }

    public void setPlace(final LoginPlace place) {
        this.place = place;
    }

    /**
     * Invoked by the ActivityManager to start a new {@link com.google.gwt.shared.Activity}.
     */
    public void start(final AcceptsOneWidget containerWidget, final EventBus eventBus) {
        this.eventBus = eventBus;
        initLoginPart(containerWidget);
        initLoginDialogPart();
        bind();
    }
    
    private void bind() {
        eventBus.addHandler(ConfigurationSelectedEvent.TYPE, this);
    }
    
    private void initLoginPart(final AcceptsOneWidget container) {
        GWT.log("Init Login Part");
        final LoginPresenter loginPresenter = injector.getLoginPresenter();
        loginPresenter.setPlace(place);
        final LoginView loginView = injector.getLoginView();
        loginView.setPresenter(loginPresenter);
        initConfigurationPart(loginView);
        initDetailPart(loginView);
        initNetworkPart(loginView);
        container.setWidget(loginView.asWidget());
    }
    
    private void initConfigurationPart(final LoginView loginView) {
        GWT.log("Init Testbed Configuration Part");
        final ConfigurationPresenter configurationPresenter = injector.getConfigurationPresenter();
        configurationPresenter.setPlace(place);
        final ConfigurationView configurationView = injector.getConfigurationView();
        configurationView.setPresenter(configurationPresenter);
        loginView.getConfigurationContainer().setWidget(configurationView);
    }
    
    private void initDetailPart(final LoginView loginView) {
        GWT.log("Init Testbed Detail Part");
        final DetailPresenter detailPresenter = injector.getDetailPresenter();
        detailPresenter.setPlace(place);
        final DetailView detailView = injector.getDetailView();
        detailView.setPresenter(detailPresenter);
        loginView.getDetailContainer().setWidget(detailView);
    }
    
    private void initNetworkPart(final LoginView loginView) {
        GWT.log("Init Testbed Network Part");
        final NetworkPresenter networkPresenter = injector.getNetworkPresenter();
        networkPresenter.setPlace(place);
        final NetworkView networkView = injector.getNetworkView();
        networkView.setPresenter(networkPresenter);
        loginView.getNetworkContainer().setWidget(networkView);
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
