package com.owen.masters.value.context;

import java.util.ArrayList;
import java.util.List;

import com.owen.masters.context.Context;
import com.owen.masters.logger.Log;
import com.owen.masters.value.Value;

public class ValueContext extends Context {

	private static List<Value> valueList;

	public ValueContext() {
		super("Values");
		valueList = new ArrayList<>();
	}

	public static List<Value> getValues() {
		return valueList;
	}

	public static void register(final Value value) {
		valueList.add(value);
		Log.log(String.format("Registered value %s (value of %s)",
				value.getIdentifier(), value.getValue().toString()));
	}
	
	public static Value get(final String name) {
		for (final Value value : valueList) {
			if (value.getIdentifier().equalsIgnoreCase(name)) {
				return value;
			}
		}
		
		return null;
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub

	}

}
