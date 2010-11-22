package de.uniluebeck.itm.webui.server;

import de.uniluebeck.itm.webui.shared.TestbedConfiguration;

import java.util.List;

public interface TestbedConfigurationService {

    List<TestbedConfiguration> getConfigurations();
}
