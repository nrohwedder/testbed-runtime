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

// Authentication credentials and other relevant information used again and again as method parameters
String urnPrefix 					= "urn:wisebed:uzl-staging:";
String username						= "testbeduzl1";
String password						= "testbeduzl1";
    
// Endpoint URLs of Authentication (SNAA), Reservation (RS) and Experimentation (iWSN) services
String snaaEndpointURL 				= "http://wisebed-staging.itm.uni-luebeck.de:8890/snaa";
String rsEndpointURL				= "http://wisebed-staging.itm.uni-luebeck.de:8889/rs";
String sessionManagementEndpointURL	= "http://wisebed-staging.itm.uni-luebeck.de:8888/sessions";

// Retrieve Java proxies of the endpoint URLs above
SNAA authenticationSystem 			= SNAAServiceHelper.getSNAAService(snaaEndpointURL);
RS reservationSystem				= RSServiceHelper.getRSService(rsEndpointURL);
SessionManagement sessionManagement = WSNServiceHelper.getSessionManagementService(sessionManagementEndpointURL); 



//--------------------------------------------------------------------------
// Application logic
//--------------------------------------------------------------------------

//--------------------------------------------------------------------------
// 1st step: authenticate with the system
//--------------------------------------------------------------------------

// build argument types
AuthenticationTriple credentials = new AuthenticationTriple();
credentials.setUrnPrefix(urnPrefix);
credentials.setUsername(username);
credentials.setPassword(password);
List credentialsList = new ArrayList();
credentialsList.add(credentials);

// do the authentication
log.info("Authenticating...");
List secretAuthenticationKeys = authenticationSystem.authenticate(credentialsList);
log.info("Successfully authenticated!");


//--------------------------------------------------------------------------
// 2nd step: reserve some nodes (here: all nodes)
//--------------------------------------------------------------------------

// retrieve the node URNs of all iSense nodes
String serializedWiseML = sessionManagement.getNetwork();
List nodeURNs = WiseMLHelper.getNodeUrns(serializedWiseML, new String[] {"isense"});
log.info("Retrieved the node URNs of all iSense nodes: {}", Arrays.toString(nodeURNs.toArray()));

// create reservation request data to reserve all iSense nodes for 10 minutes
ConfidentialReservationData reservationData = helper.generateConfidentialReservationData(
		nodeURNs,
		new Date(), 10, TimeUnit.MINUTES,
		urnPrefix, username
);

// do the reservation
log.info("Trying to reserve the following nodes: {}", nodeURNs);
List secretReservationKeys = reservationSystem.makeReservation(
		helper.copySnaaToRs(secretAuthenticationKeys),
		reservationData
);
log.info("Successfully reserved nodes: {}", nodeURNs);



//--------------------------------------------------------------------------
// 3rd step: start local controller instance
//--------------------------------------------------------------------------

//--------------------------------------------------------------------------
// 4th step: get WSN API instance URL --> call getInstance() on Session Management service
//--------------------------------------------------------------------------

log.debug("Using the following parameters for calling getInstance(): {}, {}",
		StringUtils.jaxbMarshal(helper.copyRsToWsn(secretReservationKeys)),
		localControllerEndpointURL
);

String wsnEndpointURL = sessionManagement.getInstance(
		helper.copyRsToWsn(secretReservationKeys),
		localControllerEndpointURL
);

log.info("Got an WSN instance URL, endpoint is: {}", wsnEndpointURL);
WSN wsnService = WSNServiceHelper.getWSNService(wsnEndpointURL);
final WSNAsyncWrapper wsn = WSNAsyncWrapper.of(wsnService, localControllerEndpointURL);

Controller controller = new Controller() {
	@Override
	public void receive(@WebParam(name = "msg", targetNamespace = "") final Message msg) {
		// nothing to do
	}
	@Override
	public void receiveStatus(@WebParam(name = "status", targetNamespace = "") final RequestStatus status) {
		wsn.receive(status);
	}
};

DelegatingController delegator = new DelegatingController(controller);
delegator.publish(localControllerEndpointURL);
log.info("Local controller published on url: {}", localControllerEndpointURL);

//--------------------------------------------------------------------------
// Steps 5..n: Experiment control using the WSN API
//--------------------------------------------------------------------------
log.info("Starting experiments...");

Thread.sleep(2000);

log.info("Checking if nodes are alive.");

Future areNodesAliveFuture = wsn.areNodesAlive(nodeURNs, 10, TimeUnit.SECONDS);
try {
	JobResult areNodesAliveResult = areNodesAliveFuture.get();
	log.info("RESULT: {}", areNodesAliveResult);
	if (areNodesAliveResult.getSuccessPercent() < 100) {
		System.out.println("Not all nodes are alive. Exiting...");
		System.exit(1);
	}
} catch (Exception e) {
	log.error("" + e, e);
	System.exit(1);
}

//--------------------------------------------------------------------------
// Build data structures to resemble grid topology
//--------------------------------------------------------------------------

System.out.println("Please press ENTER to continue building the topology data structures...");
System.in.read();

List nodesToAlign = new ArrayList(nodeURNs);
java.util.Collections.shuffle(nodesToAlign);

int rows = 6;
int cols = ((int) (Math.floor(nodeURNs.size()/6))) + ((nodeURNs.size() % 6 == 0) ? 0 : 1);
String[][] grid = new String[rows][];

// setup grid
for (int row=0; row<grid.length; row++) {
	grid[row] = new String[cols];
	for (int col=0; col<grid[row].length; col++) {
		if (nodesToAlign.size() > 0) {
			grid[row][col] = nodesToAlign.remove(0);
		}
	} 
}

// print grid
System.out.println();
System.out.println("===== NODE GRID =====");
for (int row=0; row<grid.length; row++) {
	for (int col=0; col<grid[row].length; col++) {
		System.out.print(grid[row][col] + " ");
	}
	System.out.println();
}

System.out.println();

// setup neighbor list
ImmutableMultimap.Builder neighborMapBuilder = ImmutableMultimap.builder();
for (int row=0; row<grid.length; row++) {
	for (int col=0; col<grid[row].length; col++) {
		// top
		if (row-1 >= 0) {
			neighborMapBuilder.put(grid[row][col], grid[row-1][col]);
		}
		// left
		if (col-1 >= 0) {
			neighborMapBuilder.put(grid[row][col], grid[row][col-1]);
		}
		// bottom
		if (row+1 < grid.length) {
			neighborMapBuilder.put(grid[row][col], grid[row+1][col]);
		}
		// right
		if (col+1 < grid[row].length) {
			neighborMapBuilder.put(grid[row][col], grid[row][col+1]);
		}
	}
}
ImmutableMultimap neighborMap = neighborMapBuilder.build();

// print neighbor list
System.out.println();
System.out.println("===== NEIGHBOR LIST =====");
for (int nodeIdx=0; nodeIdx<nodeURNs.size(); nodeIdx++) {
	String sourceNodeURN = nodeURNs.get(nodeIdx);
	System.out.print(sourceNodeURN + " => [");
	for (int neighborIdx=0; neighborIdx<neighborMap.get(sourceNodeURN).size(); neighborIdx++) {
		System.out.print(neighborMap.get(sourceNodeURN).get(neighborIdx));
		if (neighborIdx < neighborMap.get(sourceNodeURN).size() -1) {
			System.out.print(", ");
		}
	}
	System.out.println("]");
}
System.out.println();

//--------------------------------------------------------------------------
// Setup grid topology on testbed
//--------------------------------------------------------------------------

System.out.println("Please press ENTER to disable all physical links...");
System.in.read();

// disable all physical links
log.info("Disabling all physical links...");
if (!WSNHelper.disableAllPhysicalLinks(wsn, nodeURNs, 20, TimeUnit.SECONDS)) {
	System.out.println("Not all physical links could be activated. Exiting...");
	System.exit(1);
}

System.out.println();
System.out.println("Please press ENTER to set the virtual links...");
System.in.read();

log.info("Setting grid virtual links...");
if(!WSNHelper.setVirtualLinks(wsn, neighborMap, wsnEndpointURL, 20, TimeUnit.SECONDS)) {
	System.out.println("Not all virtual links could be set. Exiting...");
	System.exit(1);
}

/*Message startMsg = new Message();
BinaryMessage startBinaryMsg = new BinaryMessage();
startBinaryMsg.setBinaryData(new byte[]{0x1});
startBinaryMsg.setBinaryType((byte) 0xb);
startMsg.setBinaryMessage(startBinaryMsg);
startMsg.setSourceNodeId("urn:wisebed:uzl-staging:0xffff");
startMsg.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar) GregorianCalendar.getInstance()));
Future sendFuture = wsn.send(nodeURNs, startMsg, 10, TimeUnit.SECONDS);
if (sendFuture.get().getSuccessPercent() < 100) {
	System.out.println("Start command could not be sent to all nodes!!! Exiting...");
	System.exit(1);
}*/
   	
//--------------------------------------------------------------------------
// Steps 5..n: Shutdown experiment and exit
//--------------------------------------------------------------------------

System.out.println();
System.out.println("Done. Please press ENTER to exit.");
System.in.read();

Message stopMsg = new Message();
BinaryMessage stopBinaryMsg = new BinaryMessage();
stopBinaryMsg.setBinaryData(new byte[]{0x0});
stopBinaryMsg.setBinaryType((byte) 0xb);
stopMsg.setBinaryMessage(stopBinaryMsg);
stopMsg.setSourceNodeId("urn:wisebed:uzl-staging:0xffff");
stopMsg.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar) GregorianCalendar.getInstance()));
Future sendFuture = wsn.send(nodeURNs, stopMsg);
if (sendFuture.get().getSuccessPercent() < 100) {
    System.out.println("Start command could not be sent to all nodes!!! Exiting...");
    System.exit(1);
}

log.info("Done running experiments. Now freeing WSN service instance...");
try {
	sessionManagement.free(helper.copyRsToWsn(secretReservationKeys));
} catch (Exception e) {
	// ignore silently
}

log.info("Freed WSN service instance. Shutting down...");
System.exit(0);