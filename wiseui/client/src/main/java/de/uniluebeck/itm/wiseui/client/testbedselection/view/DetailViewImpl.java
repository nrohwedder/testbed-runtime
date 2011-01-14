package de.uniluebeck.itm.wiseui.client.testbedselection.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.SmallMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import de.uniluebeck.itm.wiseui.shared.wiseml.Coordinate;

public class DetailViewImpl extends Composite implements DetailView {

    private static DetailViewImplUiBinder uiBinder = GWT
            .create(DetailViewImplUiBinder.class);

    interface DetailViewImplUiBinder extends UiBinder<Widget, DetailViewImpl> {
    }

    private static final int DEFAULT_ZOOM_LEVEL = 0;

    private static final int ZOOM_LEVEL = 8;

    @UiField
    SimplePanel mapContainer;

    @UiField
    Label description;

    private MapWidget mapWidget;

    private Marker descriptionmMarker;
    
    private Coordinate coordinate;

    public DetailViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        initMap();
    }

    public void setPresenter(final Presenter presenter) {

    }

    private void initMap() {
        mapContainer.setSize("300px", "250px");
        Maps.loadMapsApi("", "2", false, new Runnable() {
            public void run() {
                mapWidget = new MapWidget();
                mapWidget.setSize("300px", "250px");
                mapWidget.setDoubleClickZoom(true);
                mapWidget.setContinuousZoom(true);
                mapWidget.addControl(new SmallMapControl());
                mapContainer.add(mapWidget);
                
                updateMapCoordinate();
            }
        });
    }

    public void setDescriptionCoordinate(Coordinate coordinate) {
    	this.coordinate = coordinate;
    	if (mapWidget != null) {
    		updateMapCoordinate();
    	}
    }
    
    private void updateMapCoordinate() {
    	if (descriptionmMarker != null) {
            mapWidget.removeOverlay(descriptionmMarker);
        }
    	
    	LatLng center = LatLng.newInstance(0.0, 0.0);
    	if (coordinate != null) {
	        final double x = coordinate.getX();
	        final double y = coordinate.getY();
	        center = LatLng.newInstance(x, y);
	        descriptionmMarker = new Marker(center);
	        mapWidget.addOverlay(descriptionmMarker);
	        mapWidget.setZoomLevel(ZOOM_LEVEL);
    	} else {
    		mapWidget.setZoomLevel(DEFAULT_ZOOM_LEVEL);
    	}
        mapWidget.setCenter(center);
    }

    /**
     * Returns a HasText Wrapper that allows to set a null description and set another text instead.
     */
    public HasText getDescription() {
        return description;
    }
}