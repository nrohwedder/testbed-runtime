package de.uniluebeck.itm.wiseui.client.util;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface UtilClientBundle extends ClientBundle {

	@Source("de/uniluebeck/itm/wiseui/client/util/success.png")
	ImageResource getSuccessImageResource();
	
	@Source("de/uniluebeck/itm/wiseui/client/util/warning.png")
	ImageResource getWarningImageResource();
	
	@Source("de/uniluebeck/itm/wiseui/client/util/error.png")
	ImageResource getErrorImageResource();
}
