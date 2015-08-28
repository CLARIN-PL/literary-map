
package pl.edu.pwr.services.ner;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for format.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="format">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="iob"/>
 *     &lt;enumeration value="ccl"/>
 *     &lt;enumeration value="plain"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "format")
@XmlEnum
public enum Format {

    @XmlEnumValue("iob")
    IOB("iob"),
    @XmlEnumValue("ccl")
    CCL("ccl"),
    @XmlEnumValue("plain")
    PLAIN("plain");
    private final String value;

    Format(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Format fromValue(String v) {
        for (Format c: Format.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
