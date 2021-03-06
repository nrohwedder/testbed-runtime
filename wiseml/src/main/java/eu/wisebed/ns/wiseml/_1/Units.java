//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.06.03 at 09:15:54 AM MESZ 
//


package eu.wisebed.ns.wiseml._1;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for units.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="units">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="meters"/>
 *     &lt;enumeration value="centimeters"/>
 *     &lt;enumeration value="radians"/>
 *     &lt;enumeration value="degrees"/>
 *     &lt;enumeration value="seconds"/>
 *     &lt;enumeration value="milliseconds"/>
 *     &lt;enumeration value="dBm"/>
 *     &lt;enumeration value="kelvin"/>
 *     &lt;enumeration value="percentage"/>
 *     &lt;enumeration value="lux"/>
 *     &lt;enumeration value="raw"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "units")
@XmlEnum
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-03T09:15:54+02:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
public enum Units {

	@XmlEnumValue("meters")
	METERS("meters"),
	@XmlEnumValue("centimeters")
	CENTIMETERS("centimeters"),
	@XmlEnumValue("radians")
	RADIANS("radians"),
	@XmlEnumValue("degrees")
	DEGREES("degrees"),
	@XmlEnumValue("seconds")
	SECONDS("seconds"),
	@XmlEnumValue("milliseconds")
	MILLISECONDS("milliseconds"),
	@XmlEnumValue("dBm")
	D_BM("dBm"),
	@XmlEnumValue("kelvin")
	KELVIN("kelvin"),
	@XmlEnumValue("percentage")
	PERCENTAGE("percentage"),
	@XmlEnumValue("lux")
	LUX("lux"),
	@XmlEnumValue("raw")
	RAW("raw");
	private final String value;

	Units(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static Units fromValue(String v) {
		for (Units c : Units.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
