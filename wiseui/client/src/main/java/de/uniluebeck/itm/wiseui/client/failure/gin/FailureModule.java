package de.uniluebeck.itm.wiseui.client.failure.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import de.uniluebeck.itm.wiseui.client.failure.view.FailureBoxView;
import de.uniluebeck.itm.wiseui.client.failure.view.FailureBoxViewImpl;

public class FailureModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(FailureBoxView.class).to(FailureBoxViewImpl.class).in(Singleton.class);
    }
}
