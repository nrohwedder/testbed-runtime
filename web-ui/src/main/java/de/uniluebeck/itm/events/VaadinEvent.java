/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.uniluebeck.itm.events;

/**
 *
 * @author Soenke Nommensen, adapted from com.google.gwt.event.shared.GwtEvent
 */
public abstract class VaadinEvent<H extends java.lang.reflect.InvocationHandler> {

    /**
     * Type class used to register events with the {@link HandlerManager}.
     * <p>
     * Type is parameterized by the handler type in order to make the addHandler
     * method type safe.
     * </p>
     *
     * @param <H> handler type
     */
    public static class Type<H> {

        private static int nextHashCode;
        private final int index;

        /**
         * Constructor.
         */
        public Type() {
            index = ++nextHashCode;
        }

        // We override hash code to make it as efficient as possible.
        @Override
        public final int hashCode() {
            return index;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Type<H> other = (Type<H>) obj;
            if (this.index != other.index) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "Event type";
        }
    }
    private boolean dead;
    private Object source;

    /**
     * Constructor.
     */
    protected VaadinEvent() {
    }

    /**
     * Returns the type used to register this event. Used by handler manager to
     * dispatch events to the correct handlers.
     *
     * @return the type
     */
    public abstract Type<H> getAssociatedType();

    /**
     * Returns the source that last fired this event.
     *
     * @return object representing the source of this event
     */
    public Object getSource() {
        assertLive();
        return source;
    }

    /**
     * This is a method used primarily for debugging. It gives a string
     * representation of the event details. This does not override the toString
     * method because the compiler cannot always optimize toString out correctly.
     * Event types should override as desired.
     *
     * @return a string representing the event's specifics.
     */
    public String toDebugString() {
        String name = this.getClass().getName();
        name = name.substring(name.lastIndexOf(".") + 1);
        return "event: " + name + ":";
    }

    /**
     * The toString() for abstract event is overridden to avoid accidently
     * including class literals in the the compiled output. Use {@link GwtEvent}
     * #toDebugString to get more information about the event.
     */
    @Override
    public String toString() {
        return "An event type";
    }

    /**
     * Asserts that the event still should be accessed. All events are considered
     * to be "dead" after their original handler manager finishes firing them. An
     * event can be revived by calling {@link GwtEvent#revive()}.
     */
    protected void assertLive() {
        assert (!dead) : "This event has already finished being processed by its original handler manager, so you can no longer access it";
    }

    /**
     * Should only be called by {@link HandlerManager}. In other words, do not use
     * or call.
     *
     * @param handler handler
     */
    protected abstract void dispatch(H handler);

    /**
     * Is the event current live?
     *
     * @return whether the event is live
     */
    protected final boolean isLive() {
        return !dead;
    }

    /**
     * Kill the event. After the event has been killed, users cannot really on its
     * values or functions being available.
     */
    protected void kill() {
        dead = true;
        source = null;
    }

    /**
     * Revives the event. Used when recycling event instances.
     */
    protected void revive() {
        dead = false;
        source = null;
    }

    /**
     * Set the source that triggered this event.
     *
     * @param source the source of this event, should only be set by a
     *          {@link HandlerManager}
     */
    void setSource(Object source) {
        this.source = source;
    }
}
