
package com.perso.proj.serviceinterf.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.7.9
 * Tue May 12 10:11:18 EDT 2020
 * Generated source version: 2.7.9
 */

@XmlRootElement(name = "convertC2FResponse", namespace = "http://serviceinterf.proj.perso.com")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "convertC2FResponse", namespace = "http://serviceinterf.proj.perso.com")

public class ConvertC2FResponse {

    @XmlElement(name = "return")
    private int _return;

    public int getReturn() {
        return this._return;
    }

    public void setReturn(int new_return)  {
        this._return = new_return;
    }

}

