package eu.wisebed.testbed.api.wsn.v211;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 */
@WebFault(name = "UnknownNodeUrnException", targetNamespace = "urn:WSNService")
public class UnknownNodeUrnException_Exception
		extends Exception {

	/**
	 * Java type that goes as soapenv:Fault detail element.
	 */
	private UnknownNodeUrnException faultInfo;

	/**
	 * @param message
	 * @param faultInfo
	 */
	public UnknownNodeUrnException_Exception(String message, UnknownNodeUrnException faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
	}

	/**
	 * @param message
	 * @param faultInfo
	 * @param cause
	 */
	public UnknownNodeUrnException_Exception(String message, UnknownNodeUrnException faultInfo, Throwable cause) {
		super(message, cause);
		this.faultInfo = faultInfo;
	}

	/**
	 * @return returns fault bean: eu.wisebed.testbed.api.wsn.v211.UnknownNodeUrnException
	 */
	public UnknownNodeUrnException getFaultInfo() {
		return faultInfo;
	}

}
