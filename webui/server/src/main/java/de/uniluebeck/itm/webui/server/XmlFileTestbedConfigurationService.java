package de.uniluebeck.itm.webui.server;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import de.uniluebeck.itm.webui.shared.TestbedConfiguration;


public class XmlFileTestbedConfigurationService implements TestbedConfigurationService {

	private final XStream xstream;
	
	private final Reader reader;
	
	public XmlFileTestbedConfigurationService() throws FileNotFoundException {
		this("src/main/webapp/WEB-INF/testbed-configurations.xml");
	}
	
	public XmlFileTestbedConfigurationService(String path) throws FileNotFoundException {		
		File file = new File(path);
		reader = new FileReader(file);
		xstream = new XStream();
		xstream.alias("configuration", TestbedConfiguration.class);
		xstream.useAttributeFor(TestbedConfiguration.class, "isFederated");
		xstream.addImplicitCollection(TestbedConfiguration.class, "urnPrefixList", "urnPrefix", String.class);
	}
	
	public List<TestbedConfiguration> getConfigurations() {
		List<TestbedConfiguration> result = new ArrayList<TestbedConfiguration>();
		ObjectInputStream in;
		try {
			in = xstream.createObjectInputStream(reader);
			boolean objects = true;
			while (objects) {
				try {
					TestbedConfiguration bed = (TestbedConfiguration) in.readObject();
					result.add(bed);
				} catch (EOFException e) {
					objects = false;
				}
			}
			in.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
