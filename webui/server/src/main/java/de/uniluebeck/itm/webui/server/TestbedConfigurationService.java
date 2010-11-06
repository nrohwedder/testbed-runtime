package de.uniluebeck.itm.webui.server;

import java.util.List;

import de.uniluebeck.itm.webui.shared.TestbedConfiguration;

public interface TestbedConfigurationService {

	List<TestbedConfiguration> getConfigurations();
}
