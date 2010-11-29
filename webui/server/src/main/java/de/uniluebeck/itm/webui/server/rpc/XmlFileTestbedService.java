package de.uniluebeck.itm.webui.server.rpc;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Singleton;
import com.thoughtworks.xstream.XStream;

import de.uniluebeck.itm.webui.api.TestbedService;
import de.uniluebeck.itm.webui.shared.TestbedConfiguration;
import de.uniluebeck.itm.webui.shared.exception.ConfigurationException;


@Singleton
public class XmlFileTestbedService extends RemoteServiceServlet implements TestbedService {

    private static final long serialVersionUID = 5174874924600302509L;
    private final String path;

    public XmlFileTestbedService() {
        this("src/main/webapp/WEB-INF/testbed-configurations.xml");
    }

    public XmlFileTestbedService(final String path) {
        this.path = path;
    }

    @Override
    public List<TestbedConfiguration> getConfigurations() throws ConfigurationException {
        final File file = new File(path);
        try {
            final Reader reader = new FileReader(file);
            final XStream xstream = new XStream();
            xstream.alias("configuration", TestbedConfiguration.class);
            xstream.useAttributeFor(TestbedConfiguration.class, "isFederated");
            xstream.addImplicitCollection(TestbedConfiguration.class, "urnPrefixList", "urnPrefix", String.class);

            final List<TestbedConfiguration> result = new ArrayList<TestbedConfiguration>();
            ObjectInputStream in;
            in = xstream.createObjectInputStream(reader);
            boolean objects = true;
            while (objects) {
                try {
                    final TestbedConfiguration bed = (TestbedConfiguration) in.readObject();
                    result.add(bed);
                } catch (final EOFException e) {
                    objects = false;
                }
            }
            in.close();
            return result;
        } catch (final FileNotFoundException e) {
            throw new ConfigurationException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ConfigurationException(e.getMessage(), e);
        } catch (final ClassNotFoundException e) {
            throw new ConfigurationException(e.getMessage(), e);
        }
    }
}
