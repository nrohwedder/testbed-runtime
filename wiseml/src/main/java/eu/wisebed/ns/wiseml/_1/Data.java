//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.06.03 at 09:15:54 AM MESZ 
//


package eu.wisebed.ns.wiseml._1;

import javax.annotation.Generated;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
		"content"
})
@XmlRootElement(name = "data")
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
public class Data {

	@XmlValue
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	protected String content;
	@XmlAttribute(required = true)
	@XmlSchemaType(name = "anySimpleType")
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	protected String key;

	/**
	 * Gets the value of the content property.
	 *
	 * @return possible object is
	 *         {@link String }
	 */
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	public String getContent() {
		return content;
	}

	/**
	 * Sets the value of the content property.
	 *
	 * @param value allowed object is
	 *              {@link String }
	 */
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	public void setContent(String value) {
		this.content = value;
	}

	/**
	 * Gets the value of the key property.
	 *
	 * @return possible object is
	 *         {@link String }
	 */
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	public String getKey() {
		return key;
	}

	/**
	 * Sets the value of the key property.
	 *
	 * @param value allowed object is
	 *              {@link String }
	 */
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	public void setKey(String value) {
		this.key = value;
	}

}
