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

package de.uniluebeck.itm.tr.rs.cmdline;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import de.uniluebeck.itm.tr.rs.dummy.DummyRS;
import de.uniluebeck.itm.tr.rs.federator.FederatorRS;
import de.uniluebeck.itm.tr.rs.persistence.RSPersistence;
import de.uniluebeck.itm.tr.rs.persistence.gcal.GCalRSPersistence;
import de.uniluebeck.itm.tr.rs.persistence.inmemory.InMemoryRSPersistence;
import de.uniluebeck.itm.tr.rs.persistence.jpa.RSPersistenceJPAFactory;
import de.uniluebeck.itm.tr.rs.singleurnprefix.SingleUrnPrefixRS;
import eu.wisebed.testbed.api.rs.v1.RSExceptionException;
import org.apache.commons.cli.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.xml.ws.Endpoint;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.Executors;

@SuppressWarnings("restriction")
public class RSServer {

	private static HttpServer server;

	private static final Logger log = Logger.getLogger(RSServer.class);

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String propertyFile = null;

		// create the command line parser
		CommandLineParser parser = new PosixParser();
		Options options = new Options();
		options.addOption("f", "file", true, "The properties file");
		options.addOption("v", "verbose", false, "Verbose logging output");
		options.addOption("h", "help", false, "Help output");

		try {

			CommandLine line = parser.parse(options, args);

			if (line.hasOption('v')) {
				Logger.getRootLogger().setLevel(Level.DEBUG);
			}

			if (line.hasOption('h')) {
				usage(options);
			}

			if (line.hasOption('f')) {
				propertyFile = line.getOptionValue('f');
			} else {
				throw new Exception("Please supply -f");
			}

		} catch (Exception e) {
			log.fatal("Invalid command line: " + e, e);
			usage(options);
		}

		Properties props = new Properties();
		props.load(new FileReader(propertyFile));
		startFromProperties(props);

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				log.info("Received shutdown signal. Shutting down...");
				server.stop(3);
			}
		}));

	}

	public static HttpServer startFromProperties(Properties props) throws Exception {
		int port = Integer.parseInt(props.getProperty("config.port", "8080"));

		startHttpServer(port);

		Set<String> rsNames = parseCSV(props.getProperty("config.rsnames", ""));

		String type, urnprefix, path;
		for (String rs : rsNames) {

			type = props.getProperty(rs + ".type", "");
			path = props.getProperty(rs + ".path", "/rs/" + rs);

			if ("dummy".equals(type)) {

				urnprefix = props.getProperty(rs + ".urnprefix", "urn:default:" + rs);
				startDummyRS(path, urnprefix);

			} else if ("singleurnprefix".equals(type)) {

				urnprefix = props.getProperty(rs + ".urnprefix", "urn:default:" + rs);
				startSingleUrnPrefixRS(path, urnprefix, props, rs);

			} else if ("federator".equals(type)) {

				// endpoint url -> set<urnprefix>
				Map<String, Set<String>> federatedUrnPrefixes = new HashMap<String, Set<String>>();

				Set<String> federates = parseCSV(props.getProperty(rs + ".federates", ""));
				for (String federatedName : federates) {

					Set<String> urnPrefixes = parseCSV(props.getProperty(rs + "." + federatedName + ".urnprefixes"));
					String endpointUrl = props.getProperty(rs + "." + federatedName + ".endpointurl", "");

					federatedUrnPrefixes.put(endpointUrl, urnPrefixes);

				}

				startFederator(path, federatedUrnPrefixes);

			} else {
				log.error("Found unknown type " + type + " for rs name " + rs + ". Ignoring...");
			}

		}

		return server;

	}

	private static void startSingleUrnPrefixRS(String path, String urnprefix, Properties props, String propsPrefix)
			throws Exception {

		String snaaEndpointUrl = props.getProperty(propsPrefix + ".snaaendpointurl",
				"http://localhost:8080/snaa/dummy1"
		);
		String sessionManagementEndpointUrl = props.getProperty(propsPrefix + ".sessionmanagementendpointurl");

		RSPersistence persistence = createRSPersistence(props, propsPrefix + ".persistence");

		SingleUrnPrefixRS rs = new SingleUrnPrefixRS(urnprefix, snaaEndpointUrl, sessionManagementEndpointUrl, persistence);

		HttpContext context = server.createContext(path);
		Endpoint endpoint = Endpoint.create(rs);
		endpoint.publish(context);

		log.debug("Started single urn prefix RS using persistence " + persistence + " on " + server.getAddress() + path
		);

	}

	private static RSPersistence createRSPersistence(Properties props, String propsPrefix) throws Exception {

		String persistenceType = props.getProperty(propsPrefix, "inmemory");

		if ("inmemory".equals(persistenceType)) {
			return new InMemoryRSPersistence();
		} else if ("gcal".equals(persistenceType)) {
			String userName = props.getProperty(propsPrefix + ".gcal.username");
			String password = props.getProperty(propsPrefix + ".gcal.password");
			return new GCalRSPersistence(userName, password);
		} else if ("jpa".equals(persistenceType)) {
			return createPersistenceDB(props, propsPrefix);
		}

		throw new IllegalArgumentException("Persistence type " + persistenceType + " unknown!");
	}

	private static void startDummyRS(String path, String prefix) {

		DummyRS rs = new DummyRS();

		HttpContext context = server.createContext(path);
		Endpoint endpoint = Endpoint.create(rs);
		endpoint.publish(context);

		log.debug("Started dummy RS on " + server.getAddress() + path);

	}

	private static void startHttpServer(int port) throws Exception {

		server = HttpServer.create(new InetSocketAddress(port), 5);
		// limit number of threads to 3 as that should be sufficient for a reservation system ;-)
		server.setExecutor(Executors.newCachedThreadPool(
				new ThreadFactoryBuilder().setNameFormat("RS-Thread %d").build()
		));
		server.start();

	}

	private static Set<String> parseCSV(String str) {
		String[] split = str.split(",");
		Set<String> trimmedSplit = new HashSet<String>();
		for (String string : split) {
			trimmedSplit.add(string.trim());
		}
		return trimmedSplit;
	}

	private static void usage(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(120, RSServer.class.getCanonicalName(), null, options, null);
		System.exit(1);
	}

	private static void startFederator(String path, Map<String, Set<String>>... prefixSets) {

		// union the prefix sets to one set
		Map<String, Set<String>> prefixSet = new HashMap<String, Set<String>>();
		for (Map<String, Set<String>> p : prefixSets) {
			prefixSet.putAll(p);
		}

		FederatorRS federator = new FederatorRS(prefixSet);

		HttpContext context = server.createContext(path);
		Endpoint endpoint = Endpoint.create(federator);
		endpoint.publish(context);

		log.debug("Started federator RS on " + server.getAddress() + path);

	}

	private static RSPersistence createPersistenceDB(Properties props, String propsPrefix)
			throws IOException, RSExceptionException {
		Map<String, String> properties = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			if (!((String) key).startsWith(propsPrefix + "." + "properties")) {
				continue;
			}

			String persistenceKey = ((String) key).replaceAll(propsPrefix + "." + "properties" + ".", "");
			properties.put(persistenceKey, props.getProperty((String) key));
		}

		//set Database-Persistence-TimeZone if defined in config file, if not set default to GMT)
		TimeZone persistenceTimeZone = TimeZone.getTimeZone("GMT");
		if (props.getProperty(propsPrefix + ".timezone") != null) {
			persistenceTimeZone = TimeZone.getTimeZone(props.getProperty(propsPrefix + ".timezone"));
		}

		return RSPersistenceJPAFactory.createInstance(properties, persistenceTimeZone);
	}

}