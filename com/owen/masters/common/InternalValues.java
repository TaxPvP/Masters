package com.owen.masters.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.owen.masters.value.Value;
import com.owen.masters.value.context.ValueContext;

public class InternalValues {
	
	private static List<Value> values = new ArrayList<>();
	private static Value<Boolean> tabgui = new Value<Boolean>(true, "tabgui_display");
	private static Value<Boolean> hud = new Value<Boolean>(true, "hud_display");
	private static Value<Boolean> arraylist = new Value<Boolean>(true, "arraylist_display");

	public static void init() {
		values = Arrays.asList(tabgui, hud, arraylist);
		
		values.stream().forEach(value -> {
			ValueContext.register(value);
		});
	}

}
