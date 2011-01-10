package de.uniluebeck.itm.wiseui.client.util.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

import de.uniluebeck.itm.wiseui.client.util.MessageBox;

@GinModules({UtilModule.class})
public interface UtilGinjector extends Ginjector {

	MessageBox getMessageBox();
}
