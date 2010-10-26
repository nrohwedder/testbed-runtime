/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.uniluebeck.itm.events;

/**
 *
 * @author soenke
 */
public class DefaultEventBus extends HandlerManager implements EventBus {

    public DefaultEventBus() {
        super(null);
    }
}
