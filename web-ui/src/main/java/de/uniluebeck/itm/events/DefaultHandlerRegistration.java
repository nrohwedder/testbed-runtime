/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.uniluebeck.itm.events;

/**
 *
 * @author soenke
 */
public class DefaultHandlerRegistration<H> implements HandlerRegistration {

    protected DefaultHandlerRegistration(HandlerManager manager, VaadinEvent.Type<H> type, H handler) {
    }

    public void removeHandler() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
