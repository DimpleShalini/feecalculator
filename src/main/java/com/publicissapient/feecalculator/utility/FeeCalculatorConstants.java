package com.publicissapient.feecalculator.utility;

import java.util.Arrays;
import java.util.List;

public class FeeCalculatorConstants {
	public static String HIGH_PRIORITY = "Y";
	public static String NORMAL_PRIORITY = "N";
	public static String SELL = "SELL";
	public static String BUY = "BUY";
	public static String REPORT_HEADER = "Client Id,Transaction Type,Transaction Date,Priority,Processing Fee\n";
	public static List<String> DOLLAR_100 = Arrays.asList("SELL","WITHDRAW");
	public static List<String> DOLLAR_50 = Arrays.asList("BUY","DEPOSIT");
}
