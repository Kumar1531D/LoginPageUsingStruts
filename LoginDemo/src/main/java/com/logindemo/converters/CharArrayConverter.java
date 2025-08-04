package com.logindemo.converters;

import java.util.Map;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

public class CharArrayConverter extends DefaultTypeConverter{
	
	@Override
	public Object convertValue(Map<String, Object> context, Object value, Class toType) {
        
		if(toType == char[].class && value instanceof String[]) {
			
			String values[] = (String[])value;
			
			return values[0].toCharArray();
			
		}
		
		return null;
		
    }
	
	
}
