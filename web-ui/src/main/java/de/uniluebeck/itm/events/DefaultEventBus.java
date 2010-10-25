/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.uniluebeck.itm.events;

import de.uniluebeck.itm.events.VaadinEvent.Type;
import java.lang.reflect.InvocationHandler;

/**
 *
 * @author soenke
 */
public class DefaultEventBus implements EventBus {

    public <H extends InvocationHandler> HandlerRegistration addHandler(Type<H> type, H handler) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void fireEvent(VaadinEvent event) {
        // IMPLEMENT ME!
    }

    public <H extends InvocationHandler> H getHandler(Type<H> type, int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getHandlerCount(Type<?> type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isEventHandled(Type<?> e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
