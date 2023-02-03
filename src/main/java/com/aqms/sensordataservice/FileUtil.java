package com.aqms.sensordataservice;


import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//Utility Class
public final class FileUtil {

	// Constants that can be changed based on business requirements here
	public static final String minO2 = "30.00";
	public static final String maxO2 = "100.00";
	public static final String minCO2 = "30.00";
	public static final String maxCO2 = "100.00";
	public static final String minSO2 = "30.00";
	public static final String maxSO2 = "100.00";
	public static final String minCO = "30.00";
	public static final String maxCO = "100.00";
	public static final String minC = "30.00";
	public static final String maxC = "100.00";
	public static final String maxSafe = "30.00";
	public static final BigDecimal divider = new BigDecimal("3.00");
	public static final BigDecimal threshold = new BigDecimal("65.00");
	

	// Private Constructor so that Utility class cannot be instantiated
	private FileUtil() {
	}
	

	// Logger Instantiation Method
	@SuppressWarnings("rawtypes")
	public static Logger getLogger(Class clas) {
		Logger logger = LoggerFactory.getLogger(clas);
		return logger;
	}
	

}
