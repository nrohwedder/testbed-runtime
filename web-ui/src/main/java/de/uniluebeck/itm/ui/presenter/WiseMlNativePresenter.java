/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.uniluebeck.itm.ui.presenter;

import de.uniluebeck.itm.ui.presenter.Presenter;
import de.uniluebeck.itm.common.UiUtil;
import de.uniluebeck.itm.services.SessionManagementAdapter;
import de.uniluebeck.itm.ui.view.WiseMlNativeView;

/**
 *
 * @author Soenke Nommensen
 */
public class WiseMlNativePresenter implements Presenter {

    private final Display display;

    public WiseMlNativePresenter() {
        display = new WiseMlNativeView();

        // Quick hack!
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

        display.setText(text);
    }

    public Display getDisplay() {
        return display;
    }

    public void bind() {
    }

    public interface Display extends Presenter.Display {

        public void setText(String text);
    }
}
