package de.uniluebeck.itm.webui.shared.wiseml;

import java.io.Serializable;

public class Node implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7074948156263912998L;
    
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
