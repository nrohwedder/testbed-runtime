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
 *       &lt;attribute name="source" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="target" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "enableLink")
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
public class EnableLink {

	@XmlAttribute(required = true)
	@XmlSchemaType(name = "anySimpleType")
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	protected String source;
	@XmlAttribute(required = true)
	@XmlSchemaType(name = "anySimpleType")
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	protected String target;

	/**
	 * Gets the value of the source property.
	 *
	 * @return possible object is
	 *         {@link String }
	 */
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	public String getSource() {
		return source;
	}

	/**
	 * Sets the value of the source property.
	 *
	 * @param value allowed object is
	 *              {@link String }
	 */
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	public void setSource(String value) {
		this.source = value;
	}

	/**
	 * Gets the value of the target property.
	 *
	 * @return possible object is
	 *         {@link String }
	 */
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	public String getTarget() {
		return target;
	}

	/**
	 * Sets the value of the target property.
	 *
	 * @param value allowed object is
	 *              {@link String }
	 */
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	public void setTarget(String value) {
		this.target = value;
	}

}
