package com.owen.masters.logger;

import java.util.Date;

public class Log {

	public static final void log(final Object var) {
		System.out.println(String.format("[%s] [Masters] %s",
				new Date().toGMTString(), var));
	}

}
