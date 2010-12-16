package de.uniluebeck.itm.wiseui.server.rpc;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Singleton;
import com.thoughtworks.xstream.XStream;

import de.uniluebeck.itm.wiseui.api.TestbedConfigurationService;
import de.uniluebeck.itm.wiseui.shared.TestbedConfiguration;
import de.uniluebeck.itm.wiseui.shared.exception.ConfigurationException;

@Singleton
public class TestbedConfigurationServiceImpl extends RemoteServiceServlet implements TestbedConfigurationService {

    private static final long serialVersionUID = 5174874924600302509L;
    private static final String CONFIGURATION_FILE_LOCATION = "testbed-configurations.xml";
    private final String path;

    public TestbedConfigurationServiceImpl() {
        this(CONFIGURATION_FILE_LOCATION);
    }

    public TestbedConfigurationServiceImpl(final String path) {
        this.path = path;
    }

    @Override
    public List<TestbedConfiguration> getConfigurations() throws ConfigurationException {
        try {
            final InputStream is =
                    getClass().getClassLoader().getResourceAsStream(path);
            final XStream xstream = new XStream();
            xstream.alias("configuration", TestbedConfiguration.class);
            xstream.useAttributeFor(TestbedConfiguration.class, "isFederated");
            xstream.addImplicitCollection(TestbedConfiguration.class, "urnPrefixList", "urnPrefix", String.class);

            final List<TestbedConfiguration> result = new ArrayList<TestbedConfiguration>();
            ObjectInputStream in;
            in = xstream.createObjectInputStream(is);
            boolean eof = false;
            while (!eof) {
                try {
                    final TestbedConfiguration bed = (TestbedConfiguration) in.readObject();
                    result.add(bed);
                } catch (final EOFException e) {
                    eof = true;
                }
            }
            in.close();
            return result;
        } catch (final FileNotFoundException e) {
            System.err.println(e.getMessage());
            throw new ConfigurationException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ConfigurationException(e.getMessage(), e);
        } catch (final ClassNotFoundException e) {
            throw new ConfigurationException(e.getMessage(), e);
        }
    }
}
