package de.uniluebeck.itm.wiseui.client.activity;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import de.uniluebeck.itm.wiseui.client.administration.AdministrationPlace;
import de.uniluebeck.itm.wiseui.client.experimentation.ExperimentationPlace;
import de.uniluebeck.itm.wiseui.client.testbedselection.TestbedSelectionPlace;
import de.uniluebeck.itm.wiseui.client.reservation.ReservationPlace;

/**
 * PlaceHistoryMapper interface is used to attach all places which the
 * PlaceHistoryHandler should be aware of. This is done via the @WithTokenizers
 * annotation or by extending PlaceHistoryMapperWithFactory and creating a
 * separate TokenizerFactory.
 */
@WithTokenizers({TestbedSelectionPlace.Tokenizer.class, ReservationPlace.Tokenizer.class,
        ExperimentationPlace.Tokenizer.class,
        AdministrationPlace.Tokenizer.class})
public interface WiseUiPlaceHistoryMapper extends PlaceHistoryMapper {
}
