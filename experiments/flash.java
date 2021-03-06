import java.util.*;
import java.nio.*;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import eu.wisebed.testbed.api.rs.RSServiceHelper;
import eu.wisebed.testbed.api.rs.v1.PublicReservationData;
import eu.wisebed.testbed.api.rs.v1.RS;

import eu.wisebed.testbed.api.snaa.v1.SNAA;
import eu.wisebed.testbed.api.snaa.v1.AuthenticationTriple;
import eu.wisebed.testbed.api.snaa.v1.SecretAuthenticationKey;
import eu.wisebed.testbed.api.snaa.helpers.SNAAServiceHelper;

import eu.wisebed.testbed.api.wsn.WSNServiceHelper;
import eu.wisebed.testbed.api.wsn.v211.*;

import de.uniluebeck.itm.tr.util.*;
import de.itm.uniluebeck.tr.wiseml.WiseMLHelper;

import de.uniluebeck.itm.wisebed.cmdlineclient.*;
import de.uniluebeck.itm.wisebed.cmdlineclient.wrapper.*;
import java.util.concurrent.Future;
import com.google.common.collect.*;



//--------------------------------------------------------------------------
// Configuration
//--------------------------------------------------------------------------

	// Endpoint URL of local controller instance, the testbed will use this URL to send us node outputs
	String localControllerEndpointURL	= "http://" + InetAddress.getLocalHost().getCanonicalHostName() + ":8089/controller";
	String secretReservationKeys = System.getProperty("testbed.secretreservationkeys");
	String imageToFlashPath = System.getProperty("testbed.image");
	String nodeUrnToFlash = System.getProperty("testbed.nodeurns");

	// Endpoint URLs of Authentication (SNAA), Reservation (RS) and Experimentation (iWSN) services
	String sessionManagementEndpointURL	= System.getProperty("testbed.sm.endpointurl");

	// Retrieve Java proxies of the endpoint URLs above
	SessionManagement sessionManagement = WSNServiceHelper.getSessionManagementService(sessionManagementEndpointURL);


//--------------------------------------------------------------------------
// Application logic
//--------------------------------------------------------------------------

	log.debug("Using the following parameters for calling getInstance(): {}, {}",
			StringUtils.jaxbMarshal(helper.parseSecretReservationKeys(secretReservationKeys)),
			localControllerEndpointURL
	);

	Controller controller = new Controller() {
		public void receive(Message msg) {
			// nothing to do
		}
		public void receiveStatus(RequestStatus status) {
			wsn.receive(status);
		}
	};

	DelegatingController delegator = new DelegatingController(controller);
	delegator.publish(localControllerEndpointURL);
	log.info("Local controller published on url: {}", localControllerEndpointURL);

	String wsnEndpointURL = null;
	try {
		wsnEndpointURL = sessionManagement.getInstance(
				helper.parseSecretReservationKeys(secretReservationKeys),
				localControllerEndpointURL
		);
	} catch (UnknownReservationIdException_Exception e) {
		log.warn("There was not reservation found with the given secret reservation key. Exiting.");
		System.exit(1);
	}

	log.info("Got an WSN instance URL, endpoint is: {}", wsnEndpointURL);
	WSN wsnService = WSNServiceHelper.getWSNService(wsnEndpointURL);
	final WSNAsyncWrapper wsn = WSNAsyncWrapper.of(wsnService);

	// retrieve reserved node URNs from testbed
	List nodeURNs;
	if (nodeUrnToFlash != null && !"".equals(nodeUrnToFlash)) {
		nodeURNs = Lists.newArrayList(nodeUrnToFlash.split(","));
	} else {
		nodeURNs = WiseMLHelper.getNodeUrns(wsn.getNetwork().get(), new String[]{});
	}
	log.info("Retrieved the following node URNs: {}", nodeURNs);

    log.info("Flashing nodes...");

	List programIndices;
	List programs;

	// flash isense nodes
    programIndices = new ArrayList();
    programs = new ArrayList();
    for (int i=0; i<nodeURNs.size(); i++) {
        programIndices.add(0);
    }
    programs.add(helper.readProgram(
            imageToFlashPath,
            "",
            "",
            "iSense",
            "1.0"
    ));

	Future flashFuture = wsn.flashPrograms(nodeURNs, programIndices, programs, 3, TimeUnit.MINUTES);
	JobResult flashJobResult = flashFuture.get();
	log.info("{}", flashJobResult);
	if (flashJobResult.getSuccessPercent() < 100) {
		System.out.println("Not all nodes could be flashed. Exiting");
		log.info("{}", flashFuture.get());
		System.exit(1);
	}
	
	log.info("Done. Shutting down...");
	System.exit(0);
