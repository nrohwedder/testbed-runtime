package de.uniluebeck.itm.webui.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AdministrationViewImpl extends Composite implements AdministrationView {

	private static AdministrationViewImplUiBinder uiBinder = GWT.create(AdministrationViewImplUiBinder.class);

	interface AdministrationViewImplUiBinder extends UiBinder<Widget, AdministrationViewImpl> {
	}

	public AdministrationViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		
	}
}
