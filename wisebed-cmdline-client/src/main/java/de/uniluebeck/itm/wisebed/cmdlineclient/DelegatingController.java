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

package de.uniluebeck.itm.wisebed.cmdlineclient;

import de.uniluebeck.itm.tr.util.UrlUtils;
import eu.wisebed.testbed.api.wsn.Constants;
import eu.wisebed.testbed.api.wsn.v211.Controller;
import eu.wisebed.testbed.api.wsn.v211.Message;
import eu.wisebed.testbed.api.wsn.v211.RequestStatus;
import org.apache.log4j.Logger;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.net.MalformedURLException;
import java.util.concurrent.Executors;

@WebService(serviceName = "ControllerService", targetNamespace = Constants.NAMESPACE_CONTROLLER_SERVICE, portName = "ControllerPort", endpointInterface = Constants.ENDPOINT_INTERFACE_CONTROLLER_SERVICE)
public class DelegatingController implements Controller {
	private static final Logger log = Logger.getLogger(DelegatingController.class);

	private Controller controller;

	public DelegatingController(Controller controller) {
		this.controller = controller;
	}

	public void publish(String endpointUrl) throws MalformedURLException {
		String bindAllInterfacesUrl = UrlUtils.convertHostToZeros(endpointUrl);

		log.debug("Starting DelegatingController...");
		log.debug("Endpoint URL: " + endpointUrl);
		log.debug("Binding  URL: " + bindAllInterfacesUrl);

		Endpoint endpoint = Endpoint.publish(bindAllInterfacesUrl, this);
		endpoint.setExecutor(Executors.newCachedThreadPool());

		log.debug("Successfully started DelegatingController at " + bindAllInterfacesUrl);
	}

	@Override
	public void receive(Message msg) {
		controller.receive(msg);

	}

	@Override
	public void receiveStatus(RequestStatus status) {
		controller.receiveStatus(status);
	}

}