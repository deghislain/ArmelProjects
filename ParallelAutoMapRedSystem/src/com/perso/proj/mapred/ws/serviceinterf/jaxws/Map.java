
package com.perso.proj.mapred.ws.serviceinterf.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 3.3.6
 * Sat May 23 07:43:25 EDT 2020
 * Generated source version: 3.3.6
 */

@XmlRootElement(name = "map", namespace = "http://serviceinterf.ws.mapred.proj.perso.com")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "map", namespace = "http://serviceinterf.ws.mapred.proj.perso.com")

public class Map {

    @XmlElement(name = "arg0")
    private java.util.List<java.lang.String> arg0;

    public java.util.List<java.lang.String> getArg0() {
        return this.arg0;
    }

    public void setArg0(java.util.List<java.lang.String> newArg0)  {
        this.arg0 = newArg0;
    }

}
