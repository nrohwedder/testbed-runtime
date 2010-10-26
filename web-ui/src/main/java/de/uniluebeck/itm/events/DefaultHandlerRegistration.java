package de.uniluebeck.itm.events;

import java.lang.reflect.InvocationHandler;

/**
 * Default implementation of {@link HandlerRegistration}.
 */
public class DefaultHandlerRegistration implements HandlerRegistration {

    private final HandlerManager manager;
    private final InvocationHandler handler;
    private final VaadinEvent.Type<?> type;

    /**
     * Creates a new handler registration.
     *
     * @param <H> Handler type
     *
     * @param manager the handler manager
     * @param type the event type
     * @param handler the handler
     */
    protected <H extends InvocationHandler> DefaultHandlerRegistration(
            HandlerManager manager, VaadinEvent.Type<H> type, H handler) {
        this.manager = manager;
        this.handler = handler;
        this.type = type;
    }

    /**
     * Removes the given handler from its manager.
     */
    @SuppressWarnings({"unchecked", "deprecation"})
    // This is safe because when the elements were passed in they conformed to
    // Type<H>,H.
    public void removeHandler() {
        manager.removeHandler((VaadinEvent.Type<InvocationHandler>) type, handler);
    }

    InvocationHandler getHandler() {
        return handler;
    }
}
