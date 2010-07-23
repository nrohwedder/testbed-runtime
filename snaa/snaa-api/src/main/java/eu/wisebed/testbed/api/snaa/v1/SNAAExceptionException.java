package eu.wisebed.testbed.api.snaa.v1;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 */
@WebFault(name = "SNAAFault", targetNamespace = "http://testbed.wisebed.eu/api/snaa/v1/")
public class SNAAExceptionException
		extends Exception {

	/**
	 * Java type that goes as soapenv:Fault detail element.
	 */
	private SNAAException faultInfo;

	/**
	 * @param message
	 * @param faultInfo
	 */
	public SNAAExceptionException(String message, SNAAException faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
	}

	/**
	 * @param message
	 * @param faultInfo
	 * @param cause
	 */
	public SNAAExceptionException(String message, SNAAException faultInfo, Throwable cause) {
		super(message, cause);
		this.faultInfo = faultInfo;
	}

	/**
	 * @return returns fault bean: eu.wisebed.testbed.api.snaa.v1.SNAAException
	 */
	public SNAAException getFaultInfo() {
		return faultInfo;
	}

}
