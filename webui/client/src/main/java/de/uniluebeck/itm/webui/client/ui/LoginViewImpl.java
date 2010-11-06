package de.uniluebeck.itm.webui.client.ui;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import de.uniluebeck.itm.webui.shared.NodeUrn;
import de.uniluebeck.itm.webui.shared.TestbedConfiguration;

public class LoginViewImpl extends Composite implements LoginView {

    interface LoginViewImplUiBinder extends UiBinder<Widget, LoginViewImpl> {
    }
    
    private static LoginViewImplUiBinder uiBinder = GWT.create(LoginViewImplUiBinder.class);
    
    @UiField
    SplitLayoutPanel layout;    
    
    @UiField
    SplitLayoutPanel innerlayout;
    
    @UiField
    FlowPanel configurationListPanel;
    
    @UiField
    FlowPanel nodeUrnTablePanel;
    
    @UiField
    Label description;
    
    @UiField
    Button loginButton;
    
    @UiField
    Button reloadButton;
    
    private CellList<TestbedConfiguration> configurationList;
    
    private CellTable<NodeUrn> nodeUrnTable;
    
    private Presenter presenter;

    @Inject
    public LoginViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        
        layout.setWidth("100%");
        layout.setHeight("100%");
        
        final Cell<TestbedConfiguration> cell = new AbstractCell<TestbedConfiguration>() {
			@Override
			public void render(TestbedConfiguration configuration, Object paramObject, SafeHtmlBuilder sb) {
				sb.appendEscaped(configuration.getName());
			}
        };
        configurationList = new CellList<TestbedConfiguration>(cell);
        configurationListPanel.add(configurationList);
        
        nodeUrnTable = new CellTable<NodeUrn>();
        nodeUrnTable.setWidth("100%");
        
        final TextColumn<NodeUrn> prefixColumn = new TextColumn<NodeUrn>() {
        	@Override
        	public String getValue(NodeUrn node) {
        		return node.getPrefix();
        	}
        };
        nodeUrnTable.addColumn(prefixColumn, "Prefix");
        
        final TextColumn<NodeUrn> projectColumn = new TextColumn<NodeUrn>() {
        	@Override
        	public String getValue(NodeUrn node) {
        		return node.getProject();
        	}
        };
        nodeUrnTable.addColumn(projectColumn, "Project");
        
        final TextColumn<NodeUrn> testbedColumn = new TextColumn<NodeUrn>() {
        	@Override
        	public String getValue(NodeUrn node) {
        		return node.getTestbed();
        	}
        };
        nodeUrnTable.addColumn(testbedColumn, "Testbed");
        
        final TextColumn<NodeUrn> nodeColumn = new TextColumn<NodeUrn>() {
        	@Override
        	public String getValue(NodeUrn node) {
        		return node.getNode();
        	}
		};
		nodeUrnTable.addColumn(nodeColumn, "Node");
        
        nodeUrnTablePanel.add(nodeUrnTable);
    }
    
    @UiHandler("reloadButton")
    void handleReloadClick(ClickEvent event) {
    	presenter.reload();
    }
    
    void handleLoginClick(ClickEvent event) {
    	presenter.login();
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

	@Override
	public void setConfigurations(List<TestbedConfiguration> configurations) {
		configurationList.setRowCount(configurations.size());
        configurationList.setRowData(0, configurations);
	}

	@Override
	public HasText getDescriptionField() {
		return description;
	}

	@Override
	public void setNodeUrns(List<NodeUrn> nodes) {
		nodeUrnTable.setRowCount(nodes.size());
		nodeUrnTable.setRowData(0, nodes);
	}
}
