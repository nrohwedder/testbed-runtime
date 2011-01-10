package de.uniluebeck.itm.wiseui.client.util.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import de.uniluebeck.itm.wiseui.client.util.MessageBox;
import de.uniluebeck.itm.wiseui.client.util.view.MessageBoxView;
import de.uniluebeck.itm.wiseui.client.util.view.MessageBoxViewImpl;

public class UtilModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(MessageBoxView.class).to(MessageBoxViewImpl.class).in(Singleton.class);
		bind(MessageBox.class);
	}

}
