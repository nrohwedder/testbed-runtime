/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.uniluebeck.itm.ui;

import com.vaadin.ui.AbstractComponent;
import de.uniluebeck.itm.common.UiUtil;
import de.uniluebeck.itm.services.SessionManagementAdapter;

/**
 *
 * @author soenke
 */
public class XmlController implements Presenter {

    final XmlView view;

    public XmlController() {
        view = new XmlView();

        SessionManagementAdapter sessionManagementAdapter =
                new SessionManagementAdapter("http://wisebed.itm.uni-luebeck.de:8888/sessions?wsdl");
        String text = "";
        try {
            text = sessionManagementAdapter.getNetworkAsString();
        } catch (InstantiationException ex) {
            UiUtil.showExceptionNotification(ex);
        } catch (IllegalAccessException ex) {
            UiUtil.showExceptionNotification(ex);
        }

        setText(text);
    }

    public AbstractComponent getDisplay() {
        return view;
    }

    public static void setText(String text) {
        XmlView.setText(text);
    }

}
