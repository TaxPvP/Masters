package com.owen.masters.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Commons {

	public static final boolean IS_OUTDATED() {
		try {
			return getIsOutdated();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private static double getVersion() {
		return 2.1;
	}

	public static final char COMMAND_PREFIX() {
		return '-';
	}

	public static final String BASE_DIRECTORY() {
		return "Masters";
	}
	
	private static final boolean getIsOutdated() {
		 try {
		        URL url = new URL("http://masters.x10host.com/VersionChecker.php");       

		        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		        String str;
		        while ((str = in.readLine()) != null) {
		            str = in.readLine().toString();
		            
		            if (str.contains(":")) {
		            	final double version = Double.parseDouble(str.split(":")[1]);
		            	return version != getVersion();
		            }
		            
		        }
		        in.close();
		    } catch (MalformedURLException e) {
		    	e.printStackTrace();
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		
		return false;
	}
}
