/**
 * 
 */
package com.perso.proj.client.model;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;



import com.perso.proj.serviceinterf.SmallServices;

/**
 * @author deghislain
 * This class provide the business logic for our small services WS
 */
public class SmallServicesModel {
	//proxy used to call the WS
	SmallServices proxy = getTheProxy();
	public String convertTemperature(int temp, char from) {
		return this.convert(temp, from);
	}
	
	//call the converter service trough the proxy
	private String convert(int temp, char from) {
		int nTemp = 0;
		String result = "";
		if(null != proxy) {
			if(from == 'C') {
				result = temp + " Degrees Celsius in Fahrenheit is: ";
				nTemp = proxy.convertC2F(temp);
			}else {
				nTemp = proxy.convertF2C(temp);
				result = temp + " Fahrenheit in Degrees Celssius is: ";
			}
		}
		result += Integer.toString(nTemp);
		return result;
	}
	
	public String sortString(String str) {
		return this.doTheSorting(str);
	}
	
	private String doTheSorting(String str) {
		String newStr = "";
		if(null != proxy) {
			newStr = proxy.sort(str);
		}
		if(newStr == null || newStr.isEmpty()) {
			newStr = "No Result: The service might not be running";
		}
		return newStr;
	}
	private SmallServices getTheProxy() {
		Service smallServices = null;
		SmallServices proxy = null;
		try {
			URL url = new URL("http://localhost:8080/SoapWSAppSystem/services/SmallServicesImplPort?wsdl");
			//we use targetNamespace and serviceName from WS (SmallServices interface) to create the QName
			QName qn = new QName("http://serviceinterf.proj.perso.com", "SmallServicesImpl");
			smallServices = Service.create(url, qn);
			proxy = smallServices.getPort(SmallServices.class);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return proxy;
	}
	

}
