package de.uniluebeck.itm.events;

import java.lang.reflect.InvocationHandler;

/**
 * Handles a presenter revelation event.
 * 
 * @author David Peterson
 * 
 */
public interface PresenterRevealedHandler extends InvocationHandler {

    void onPresenterRevealed( PresenterRevealedEvent event );

}
