
package com.perso.proj.mapred.ws.serviceinterf.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 3.3.6
 * Mon May 25 15:16:22 EDT 2020
 * Generated source version: 3.3.6
 */

@XmlRootElement(name = "combineResponse", namespace = "http://serviceinterf.ws.mapred.proj.perso.com")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "combineResponse", namespace = "http://serviceinterf.ws.mapred.proj.perso.com")

public class CombineResponse {

    @XmlElement(name = "return")
    private java.util.HashMap<java.lang.String, java.lang.Integer> _return;

    public java.util.HashMap<java.lang.String, java.lang.Integer> getReturn() {
        return this._return;
    }

    public void setReturn(java.util.HashMap<java.lang.String, java.lang.Integer> new_return)  {
        this._return = new_return;
    }

}
