/**********************************************************************************************************************
 * Copyright (c) 2010, Institute of Telematics, University of Luebeck                                                  *
 * All rights reserved.                                                                                               *
 *                                                                                                                    *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the   *
 * following conditions are met:                                                                                      *
 *                                                                                                                    *
 * - Redistributions of source code must retain the above copyright notice, this list of conditions and the following *
 *   disclaimer.                                                                                                      *
 * - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the        *
 *   following disclaimer in the documentation and/or other materials provided with the distribution.                 *
 * - Neither the name of the University of Luebeck nor the names of its contributors may be used to endorse or promote *
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

package de.uniluebeck.itm.tr.runtime.portalapp;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import de.uniluebeck.itm.gtr.common.Service;
import de.uniluebeck.itm.tr.runtime.wsnapp.WSNApp;
import eu.wisebed.testbed.api.wsn.v211.WSN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;


@Singleton
public class WSNServiceHandle implements Service {

	private static final Logger log = LoggerFactory.getLogger(WSNServiceHandle.class);

	private WSNService wsnService;

	private WSNApp wsnApp;

	private URL wsnInstanceEndpointUrl;

	@Inject
	WSNServiceHandle(@Named(WSNServiceModule.WSN_SERVICE_ENDPOINT_URL) URL wsnInstanceEndpointUrl,
					 WSNService wsnService,
					 WSNApp wsnApp) {

		this.wsnService = wsnService;
		this.wsnApp = wsnApp;
		this.wsnInstanceEndpointUrl = wsnInstanceEndpointUrl;
	}

	@Override
	public void start() throws Exception {
		wsnApp.start();
		wsnService.start();
	}

	@Override
	public void stop() {
		try {
			wsnService.stop();
		} catch (Exception e) {
			log.warn("" + e, e);
		}
		try {
			wsnApp.stop();
		} catch (Exception e) {
			log.warn("" + e, e);
		}
	}

	public WSN getWSNService() {
		return wsnService;
	}

	public WSNApp getWsnApp() {
		return wsnApp;
	}

	public URL getWsnInstanceEndpointUrl() {
		return wsnInstanceEndpointUrl;
	}
	
}