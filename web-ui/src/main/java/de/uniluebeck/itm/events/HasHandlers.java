/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.uniluebeck.itm.events;

/**
 *
 * @author soenke
 */
public interface HasHandlers {

    public void fireEvent(VaadinEvent<?> event);
}