/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.uniluebeck.itm.ui.presenter;

import de.uniluebeck.itm.common.UiUtil;
import de.uniluebeck.itm.events.EventBus;
import de.uniluebeck.itm.services.SessionManagementAdapter;

/**
 *
 * @author Soenke Nommensen
 */
public class WiseMlNativePresenter extends BasicPresenter<WiseMlNativePresenter.Display> {

    public WiseMlNativePresenter(WiseMlNativePresenter.Display display, EventBus eventBus) {
        super(display, eventBus);

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

    @Override
    protected void onBind() {
    }

    @Override
    protected void onUnbind() {
    }

    @Override
    protected void onRevealDisplay() {
    }

    public interface Display extends Presenter.Display {

        public void setText(String text);
    }
}
