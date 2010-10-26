package de.uniluebeck.itm.events;

import java.lang.reflect.InvocationHandler;

public interface PresenterChangedHandler extends InvocationHandler {

    void onPresenterChanged(PresenterChangedEvent event);
}
