//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.05.07 at 06:46:11 PM MESZ 
//


package de.uniluebeck.itm.tr.xml;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * This defines all server side connections (e.g., a TCP server socket)
 * <p/>
 * <p>Java class for ServerConnections complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ServerConnections">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="serverconnection" type="{http://itm.uniluebeck.de/tr/xml}ServerConnection" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServerConnections", propOrder = {
		"serverconnection"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-05-07T06:46:11+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
public class ServerConnections {

	@XmlElement(required = true)
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-05-07T06:46:11+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	protected List<ServerConnection> serverconnection;

	/**
	 * Gets the value of the serverconnection property.
	 * <p/>
	 * <p/>
	 * This accessor method returns a reference to the live list,
	 * not a snapshot. Therefore any modification you make to the
	 * returned list will be present inside the JAXB object.
	 * This is why there is not a <CODE>set</CODE> method for the serverconnection property.
	 * <p/>
	 * <p/>
	 * For example, to add a new item, do as follows:
	 * <pre>
	 *    getServerconnection().add(newItem);
	 * </pre>
	 * <p/>
	 * <p/>
	 * <p/>
	 * Objects of the following type(s) are allowed in the list
	 * {@link ServerConnection }
	 */
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-05-07T06:46:11+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	public List<ServerConnection> getServerconnection() {
		if (serverconnection == null) {
			serverconnection = new ArrayList<ServerConnection>();
		}
		return this.serverconnection;
	}

}
