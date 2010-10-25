/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.uniluebeck.itm.ui;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author soenke
 */
public class WiseMlNativeView extends VerticalLayout implements WiseMlNativePresenter.Display {

    static TextField txtWiseMl;

    public WiseMlNativeView() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);

        txtWiseMl = new TextField();
        txtWiseMl.setRows(50);
        txtWiseMl.setColumns(80);

        addComponent(txtWiseMl);
    }

    public void setText(String text) {
        txtWiseMl.setValue(text);
    }

    public AbstractComponent asWidget() {
        return this;
    }
}
