package de.uniluebeck.itm.webui.shared.wiseml;

import java.io.Serializable;
import java.util.List;


public class Setup implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -298010549586832532L;
    
    private Coordinate origin;
    
    private String coordinateType;
    
    private String description;
    
    private List<Node> nodes;

    public Coordinate getOrigin() {
        return origin;
    }

    public void setOrigin(Coordinate origin) {
        this.origin = origin;
    }

    public String getCoordinateType() {
        return coordinateType;
    }

    public void setCoordinateType(String coordinateType) {
        this.coordinateType = coordinateType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

}
