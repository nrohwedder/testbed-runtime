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
 *       &lt;sequence>
 *         &lt;element ref="{http://wisebed.eu/ns/wiseml/1.0}name"/>
 *         &lt;element ref="{http://wisebed.eu/ns/wiseml/1.0}datatype"/>
 *         &lt;element name="unit" type="{http://wisebed.eu/ns/wiseml/1.0}units"/>
 *         &lt;element ref="{http://wisebed.eu/ns/wiseml/1.0}default"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
		"name",
		"datatype",
		"unit",
		"_default"
})
@XmlRootElement(name = "capability")
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
public class Capability {

	@XmlElement(required = true)
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	protected String name;
	@XmlElement(required = true)
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	protected Dtypes datatype;
	@XmlElement(required = true)
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	protected Units unit;
	@XmlElement(name = "default", required = true)
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	protected String _default;

	/**
	 * Gets the value of the name property.
	 *
	 * @return possible object is
	 *         {@link String }
	 */
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 *
	 * @param value allowed object is
	 *              {@link String }
	 */
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * Gets the value of the datatype property.
	 *
	 * @return possible object is
	 *         {@link Dtypes }
	 */
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	public Dtypes getDatatype() {
		return datatype;
	}

	/**
	 * Sets the value of the datatype property.
	 *
	 * @param value allowed object is
	 *              {@link Dtypes }
	 */
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	public void setDatatype(Dtypes value) {
		this.datatype = value;
	}

	/**
	 * Gets the value of the unit property.
	 *
	 * @return possible object is
	 *         {@link Units }
	 */
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	public Units getUnit() {
		return unit;
	}

	/**
	 * Sets the value of the unit property.
	 *
	 * @param value allowed object is
	 *              {@link Units }
	 */
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	public void setUnit(Units value) {
		this.unit = value;
	}

	/**
	 * Gets the value of the default property.
	 *
	 * @return possible object is
	 *         {@link String }
	 */
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	public String getDefault() {
		return _default;
	}

	/**
	 * Sets the value of the default property.
	 *
	 * @param value allowed object is
	 *              {@link String }
	 */
	@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
	public void setDefault(String value) {
		this._default = value;
	}

}
