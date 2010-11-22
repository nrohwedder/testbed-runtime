package de.uniluebeck.itm.webui.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class WiseMLNativeViewImpl extends Composite implements WiseMLNativeView {

    private static WiseMLNativeViewImplUiBinder uiBinder = GWT.create(WiseMLNativeViewImplUiBinder.class);

    interface WiseMLNativeViewImplUiBinder extends
            UiBinder<Widget, WiseMLNativeViewImpl> {
    }

    public WiseMLNativeViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setPresenter(Presenter presenter) {

    }

}
