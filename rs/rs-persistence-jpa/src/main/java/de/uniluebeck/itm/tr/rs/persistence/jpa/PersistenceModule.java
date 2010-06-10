/**********************************************************************************************************************
 * Copyright (c) 2010, Institute of Telematics, University of Luebeck                                                 *
 * All rights reserved.                                                                                               *
 *                                                                                                                    *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the   *
 * following conditions are met:                                                                                      *
 *                                                                                                                    *
 * - Redistributions of source code must retain the above copyright notice, this list of conditions and the following *
 *   disclaimer.                                                                                                      *
 * - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the        *
 *   following disclaimer in the documentation and/or other materials provided with the distribution.                 *
 * - Neither the name of the University of Luebeck nor the names of its contributors may be used to endorse or promote*
 *   products derived from this software without specific prior written permission.                                   *
 *                                                                                                                    *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, *
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE      *
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,         *
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE *
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF    *
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY   *
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.                                *
 **********************************************************************************************************************/

package de.uniluebeck.itm.tr.rs.persistence.jpa;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import de.uniluebeck.itm.tr.rs.persistence.RSPersistence;
import de.uniluebeck.itm.tr.rs.persistence.jpa.impl.RSPersistenceImpl;
import eu.wisebed.testbed.api.rs.v1.RSException;
import eu.wisebed.testbed.api.rs.v1.RSExceptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * UserInternal: nr
 * Date: 09.04.2010
 * Time: 16:28:09
 */
public class PersistenceModule extends AbstractModule {

	private static final Logger log = LoggerFactory.getLogger(PersistenceModule.class);

    /*  -- old --
        public static RSPersistence createInstance(String persistenceUnitName) {
        Injector injector = Guice.createInjector(new PersistenceModule(persistenceUnitName));
        return injector.getInstance(RSPersistence.class);
    }*/

    public static RSPersistence createInstance(Map properties){
        Injector injector = Guice.createInjector(new PersistenceModule(properties));
        return injector.getInstance(RSPersistence.class);
    }

  /* -- old --
    //creating RSPersistence-Instance from configFile

    public static RSPersistence createInstanceFromWithConfigFile(String persistenceUnitName, File configFile) throws URISyntaxException, IOException, RSExceptionException {

		// TODO this does not work when built as jar, re-write

		if (!configFile.exists())
            throw new RSExceptionException("Could not find config-file: \"" + configFile.getAbsolutePath() + "\"", new RSException());

		URL persistenceURL = PersistenceModule.class.getClassLoader().getResource("META-INF/persistence.xml");

        //backup old persistence.xml
        File existingConfigFile = new File(persistenceURL.toURI());
        File backUpFile = new File(existingConfigFile.getParent(), "persistence.xml_old");
        existingConfigFile.renameTo(backUpFile);
        existingConfigFile = backUpFile;

        //save config-filename
        String origConfigFileName = configFile.getAbsolutePath();
        // Copy the config-file as persistence.xml to META-INF-folder.
        File newConfigFile = new File(existingConfigFile.getParent(), "persistence.xml");
        configFile.renameTo(newConfigFile);
        configFile = newConfigFile;

        RSPersistence persistenceInstance = null;
        try {
            persistenceInstance = createInstance(persistenceUnitName);
        }
        catch (Exception e){
            restoreConfigFile(configFile, origConfigFileName);
            restoreExistingConfigFile(existingConfigFile);
            RSException rsException = new RSException();
            rsException.setMessage(e.getMessage());
            throw new RSExceptionException("Exception occured while creating instance of JPA-Persistence", rsException);
        }
        //write back persistence-config-file
        restoreConfigFile(configFile, origConfigFileName);
        //rename backuped persistence-xml
        restoreExistingConfigFile(existingConfigFile);

        return persistenceInstance;
    }

    public static void restoreConfigFile(File configFile, String origConfigFileName){
        configFile.renameTo(new File(origConfigFileName));
    }

    public static void restoreExistingConfigFile(File existingConfigFile){
        existingConfigFile.renameTo(new File(existingConfigFile.getParent(), "persistence.xml"));
    }

    **/

    private Map properties;

    /* --- old ---
        public PersistenceModule(String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
    }*/

    public PersistenceModule(Map properties){
        this.properties = properties;
    }

    @Override
    protected void configure() {
        bind(Map.class).annotatedWith(Names.named("properties")).toInstance(properties);
        bind(RSPersistence.class).to(RSPersistenceImpl.class);
    }

}
