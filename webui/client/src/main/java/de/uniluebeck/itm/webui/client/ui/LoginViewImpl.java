package de.uniluebeck.itm.webui.client.ui;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.SmallMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionModel;
import com.google.inject.Inject;

import de.uniluebeck.itm.webui.shared.TestbedConfiguration;
import de.uniluebeck.itm.webui.shared.wiseml.Coordinate;
import de.uniluebeck.itm.webui.shared.wiseml.Node;

public class LoginViewImpl extends Composite implements LoginView {

    interface LoginViewImplUiBinder extends UiBinder<Widget, LoginViewImpl> {
    }
    private static LoginViewImplUiBinder uiBinder = GWT.create(LoginViewImplUiBinder.class);
    
    private static final int ZOOM_LEVEL = 8;
    @UiField
    CellList<TestbedConfiguration> configurationList;
    @UiField
    CellTable<Node> nodeTable;
    @UiField
    Label description;
    @UiField
    Button loginButton;
    @UiField
    Button reloadButton;
    @UiField
    SimplePanel mapContainer;
    private MapWidget mapWidget;
    private LoginDialog loginDialog = new LoginDialog();
    private Presenter presenter;
    private Marker descriptionmMarker;

    @Inject
    public LoginViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));

        nodeTable.setWidth("100%");

        final TextColumn<Node> idColumn = new TextColumn<Node>() {

            @Override
            public String getValue(final Node node) {
                return node.getId();
            }
        };
        nodeTable.addColumn(idColumn, "ID");

        final TextColumn<Node> nodeTypeColumn = new TextColumn<Node>() {

            @Override
            public String getValue(final Node node) {
                return node.getNodeType();
            }
        };
        nodeTable.addColumn(nodeTypeColumn, "Node-Type");

        final TextColumn<Node> descriptionColumn = new TextColumn<Node>() {

            @Override
            public String getValue(final Node node) {
                return node.getDescription();
            }
        };
        nodeTable.addColumn(descriptionColumn, "Description");
        
        initMap();
    }

    @UiFactory
    public CellList<TestbedConfiguration> createTestbedConfigurationCellList() {
        final Cell<TestbedConfiguration> cell = new AbstractCell<TestbedConfiguration>() {

            @Override
            public void render(final TestbedConfiguration configuration,
                    final Object paramObject, final SafeHtmlBuilder sb) {
                sb.appendHtmlConstant("<div class=\"celllist-entry\">");
                sb.appendEscaped(configuration.getName());
                sb.appendHtmlConstant("</div>");
            }
        };
        return new CellList<TestbedConfiguration>(cell);
    }
    
    private void initMap() {
        mapContainer.setSize("300px", "250px");
        Maps.loadMapsApi("", "2", false, new Runnable() {
            public void run() {
                mapWidget = new MapWidget();
                mapWidget.setSize("300px", "250px");
                mapWidget.setDoubleClickZoom(true);
                mapWidget.setContinuousZoom(true);
                mapWidget.addControl(new SmallMapControl());
                mapContainer.add(mapWidget);
            }
        });
    }

    @UiHandler("reloadButton")
    public void handleReloadClick(final ClickEvent event) {
        presenter.reload();
    }

    @UiHandler("loginButton")
    public void handleLoginClick(final ClickEvent event) {
        presenter.showLoginDialog();
    }

    public void setPresenter(final Presenter presenter) {
        this.presenter = presenter;
        loginDialog.setPresenter(presenter);
    }

    public void setConfigurations(final List<TestbedConfiguration> configurations) {
        configurationList.setRowCount(configurations.size());
        configurationList.setRowData(0, configurations);
    }
    
    public void setDescriptionCoordinate(final Coordinate coordinate) {
        final double x = coordinate.getX();
        final double y = coordinate.getY();
        final LatLng center = LatLng.newInstance(x, y);
        if (descriptionmMarker != null) {
            mapWidget.removeOverlay(descriptionmMarker);
        }
        descriptionmMarker = new Marker(center);
        mapWidget.addOverlay(descriptionmMarker);
        mapWidget.setCenter(center);
        mapWidget.setZoomLevel(ZOOM_LEVEL);
    }

    public HasText getDescriptionText() {
        return description;
    }

    public void setNodes(final List<Node> nodes) {
        nodeTable.setRowCount(nodes.size());
        nodeTable.setPageSize(nodes.size());
        nodeTable.setRowData(0, nodes);
    }

    public void setTestbedConfigurationSelectionModel(
            final SelectionModel<TestbedConfiguration> selectionModel) {
        configurationList.setSelectionModel(selectionModel);
    }

    public void showLoginDialog(final String title) {
        loginDialog.setText(title);
        loginDialog.show();
        loginDialog.center();
    }

    public void hideLoginDialog() {
        loginDialog.hide();
    }

    public HasText getUsernameText() {
        return loginDialog.getUsernameTextBox();
    }

    public HasText getPasswordText() {
        return loginDialog.getPasswordTextBox();
    }

    public void addError(final String error) {
        loginDialog.addError(error);
    }

    public void clearErrors() {
        loginDialog.clearErrors();
    }

    public void disableLoginForm() {
    }

    public HasEnabled getUsernameEnabled() {
        return loginDialog.getUsernameTextBox();
    }

    public HasEnabled getPasswordEnabled() {
        return loginDialog.getPasswordTextBox();
    }

    public HasEnabled getLoginEnabled() {
        return loginButton;
    }

    public HasEnabled getSubmitEnabled() {
        return loginDialog.getSubmitButton();
    }

    public HasEnabled getReloadEnabled() {
        return reloadButton;
    }
}
