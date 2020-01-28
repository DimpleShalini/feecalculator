package com.publicissapient.feecalculator.service;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FeeCalculatorService {
	String getSummaryReport() throws FileNotFoundException, IOException;
}
