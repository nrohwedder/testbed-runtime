package de.uniluebeck.itm.webui.shared.wiseml;

import java.io.Serializable;

public class Wiseml implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5604418503671696867L;

    private String version;
    
    private Setup setup;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Setup getSetup() {
        return setup;
    }

    public void setSetup(Setup setup) {
        this.setup = setup;
    }
}
