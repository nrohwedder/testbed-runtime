package de.uniluebeck.itm.webui.client.ui;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionModel;
import com.google.inject.Inject;

import de.uniluebeck.itm.webui.shared.NodeUrn;
import de.uniluebeck.itm.webui.shared.TestbedConfiguration;

public class LoginViewImpl extends Composite implements LoginView {

	public class LoginDialog extends DialogBox {
		
		private final TextBox usernameTextBox = new TextBox();
		
		private final PasswordTextBox passwordTextBox = new PasswordTextBox();
		
		private final Button submitButton = new Button("Submit");
		
		private final Button cancelButton = new Button("Cancel");
		
		private final VerticalPanel errorPanel = new VerticalPanel();
		
		private Presenter presenter;
		
		public LoginDialog() {
			usernameTextBox.setWidth("200px");
			passwordTextBox.setWidth("200px");
			
			final VerticalPanel panel = new VerticalPanel();
			final FlexTable loginTable = new FlexTable();
			loginTable.setText(0, 0, "Username:");
			loginTable.setWidget(0, 1, usernameTextBox);
			loginTable.setText(1, 0, "Password:");
			loginTable.setWidget(1, 1, passwordTextBox);
			panel.add(loginTable);
			
			errorPanel.setStyleName("error");
			panel.add(errorPanel);
			
			submitButton.setWidth("50px");
			cancelButton.setWidth("50px");
			
			final HorizontalPanel buttonPanel = new HorizontalPanel();
			buttonPanel.setSpacing(5);
			buttonPanel.add(submitButton);
			buttonPanel.add(cancelButton);
			
			panel.add(buttonPanel);
			panel.setCellHorizontalAlignment(buttonPanel, HasHorizontalAlignment.ALIGN_RIGHT);
			
			setWidget(panel);
			setModal(true);
			setGlassEnabled(true);
			setAnimationEnabled(true);
		}
		
		private void bind() {
			submitButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent paramClickEvent) {
					presenter.submit();
				}
			});
			cancelButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent paramClickEvent) {
					presenter.hideLoginDialog();
				}
			});
		}
		
		public void setPresenter(Presenter presenter) {
			this.presenter = presenter;
			bind();
		}
		
		public TextBox getUsernameTextBox() {
			return usernameTextBox;
		}
		
		public TextBox getPasswordTextBox() {
			return passwordTextBox;
		}
		
		public Button getSubmitButton() {
			return submitButton;
		}
		
		public Button getCancelButton() {
			return cancelButton;
		}
		
		public void addError(String error) {
			errorPanel.add(new Label(error));
		}
		
		public void clearErrors() {
			errorPanel.clear();
		}
	}
	
    interface LoginViewImplUiBinder extends UiBinder<Widget, LoginViewImpl> {
    }
    
    private static LoginViewImplUiBinder uiBinder = GWT.create(LoginViewImplUiBinder.class);
    
    @UiField
    DockLayoutPanel layout;    
    
    @UiField
    SplitLayoutPanel innerlayout;
    
    @UiField
    CellList<TestbedConfiguration> configurationList;
    
    @UiField
    CellTable<NodeUrn> nodeUrnTable;
    
    @UiField
    Label description;
    
    @UiField
    Button loginButton;
    
    @UiField
    Button reloadButton;
    
    private LoginDialog loginDialog = new LoginDialog();
    
    private Presenter presenter;

    @Inject
    public LoginViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        
        layout.setWidth("100%");
        layout.setHeight("100%");
        
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
    }
    
    @UiFactory
    public CellList<TestbedConfiguration> createTestbedConfigurationCellList() {
        final Cell<TestbedConfiguration> cell = new AbstractCell<TestbedConfiguration>() {
			@Override
			public void render(TestbedConfiguration configuration, Object paramObject, SafeHtmlBuilder sb) {
				sb.appendEscaped(configuration.getName());
			}
        };
        return new CellList<TestbedConfiguration>(cell);
    }
    
    @UiHandler("reloadButton")
    void handleReloadClick(ClickEvent event) {
    	presenter.reload();
    }
    
    @UiHandler("loginButton")
    void handleLoginClick(ClickEvent event) {
    	presenter.showLoginDialog();
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        loginDialog.setPresenter(presenter);
    }

	@Override
	public void setConfigurations(List<TestbedConfiguration> configurations) {
		configurationList.setRowCount(configurations.size());
        configurationList.setRowData(0, configurations);
	}

	@Override
	public HasText getDescriptionText() {
		return description;
	}

	@Override
	public void setNodeUrns(List<NodeUrn> nodes) {
		nodeUrnTable.setRowCount(nodes.size());
		nodeUrnTable.setRowData(0, nodes);
	}

	@Override
	public void setTestbedConfigurationSelectionModel(SelectionModel<TestbedConfiguration> selectionModel) {
		configurationList.setSelectionModel(selectionModel);
	}

	@Override
	public void showLoginDialog(String title) {
		loginDialog.setText(title);
		loginDialog.show();
		loginDialog.center();
	}
	
	@Override
	public void hideLoginDialog() {
		loginDialog.hide();
	}

	@Override
	public HasText getUsernameText() {
		return loginDialog.getUsernameTextBox();
	}

	@Override
	public HasText getPasswordText() {
		return loginDialog.getPasswordTextBox();
	}

	@Override
	public void addError(String error) {
		loginDialog.addError(error);
	}

	@Override
	public void clearErrors() {
		loginDialog.clearErrors();
	}

	@Override
	public void disableLoginForm() {
		
	}

	@Override
	public HasEnabled getUsernameEnabled() {
		return loginDialog.getUsernameTextBox();
	}

	@Override
	public HasEnabled getPasswordEnabled() {
		return loginDialog.getPasswordTextBox();
	}

	@Override
	public HasEnabled getLoginEnabled() {
		return loginButton;
	}

	@Override
	public HasEnabled getSubmitEnabled() {
		return loginDialog.getSubmitButton();
	}

	@Override
	public HasEnabled getReloadEnabled() {
		return reloadButton;
	}
}
