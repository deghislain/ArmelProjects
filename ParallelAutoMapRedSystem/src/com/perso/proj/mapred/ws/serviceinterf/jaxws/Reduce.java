
package com.perso.proj.mapred.ws.serviceinterf.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 3.3.6
 * Sun May 24 18:07:51 EDT 2020
 * Generated source version: 3.3.6
 */

@XmlRootElement(name = "reduce", namespace = "http://serviceinterf.ws.mapred.proj.perso.com")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reduce", namespace = "http://serviceinterf.ws.mapred.proj.perso.com")

public class Reduce {

    @XmlElement(name = "arg0")
    private java.util.List<com.perso.proj.mapred.ws.entity.KeyValuePair> arg0;

    public java.util.List<com.perso.proj.mapred.ws.entity.KeyValuePair> getArg0() {
        return this.arg0;
    }

    public void setArg0(java.util.List<com.perso.proj.mapred.ws.entity.KeyValuePair> newArg0)  {
        this.arg0 = newArg0;
    }

}

