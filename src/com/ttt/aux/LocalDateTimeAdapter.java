package com.ttt.aux;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adapter (for JAXB) to convert between the LocalDateTime and the ISO 8601 
 * String representation of the date such as '2015-04-25T17:48:18.319'.
 * 
 * @author chrbirks
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

	@Override
	public LocalDateTime unmarshal(String v) throws Exception {
		return LocalDateTime.parse(v);
	}

	@Override
	public String marshal(LocalDateTime v) throws Exception {
		return v.toString();
	}
	
}
