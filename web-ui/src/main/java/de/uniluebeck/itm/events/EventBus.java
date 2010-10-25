/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.uniluebeck.itm.events;

/**
 * An interface providing minimal access to an {@link EventHandler} manager.
 *
 * Rather than being attached to a single object, an EventBus provides a central
 * pathway to send events across the whole application.
 *
 * @author David Peterson, adapted by Soenke Nommensen
 */
public interface EventBus {
    <H extends java.lang.reflect.InvocationHandler> HandlerRegistration addHandler( VaadinEvent.Type<H> type, H handler );

    void fireEvent( VaadinEvent event );

    <H extends java.lang.reflect.InvocationHandler> H getHandler( VaadinEvent.Type<H> type, int index );

    int getHandlerCount( VaadinEvent.Type<?> type );

    boolean isEventHandled( VaadinEvent.Type<?> e );
}
