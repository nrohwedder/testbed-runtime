package de.uniluebeck.itm.webui.client.mvp;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import de.uniluebeck.itm.webui.client.place.AdministrationPlace;
import de.uniluebeck.itm.webui.client.place.ExperimentationPlace;
import de.uniluebeck.itm.webui.client.place.LoginPlace;
import de.uniluebeck.itm.webui.client.place.ReservationPlace;
import de.uniluebeck.itm.webui.client.place.WiseMLNativePlace;

/**
 * PlaceHistoryMapper interface is used to attach all places which the
 * PlaceHistoryHandler should be aware of. This is done via the @WithTokenizers
 * annotation or by extending PlaceHistoryMapperWithFactory and creating a
 * separate TokenizerFactory.
 */
@WithTokenizers({ LoginPlace.Tokenizer.class, ReservationPlace.Tokenizer.class,
        ExperimentationPlace.Tokenizer.class,
        AdministrationPlace.Tokenizer.class, WiseMLNativePlace.Tokenizer.class })
public interface WebUiPlaceHistoryMapper extends PlaceHistoryMapper {
}
