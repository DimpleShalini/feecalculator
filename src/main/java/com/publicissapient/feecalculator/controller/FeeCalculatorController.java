package com.publicissapient.feecalculator.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.publicissapient.feecalculator.service.FeeCalculatorService;

@RestController
@RequestMapping("/feecalculator")
public class FeeCalculatorController {

	@Autowired
	FeeCalculatorService feeCalculatorService;
	
	@RequestMapping("/version")
	public String version(){
		return "FeeCalculatorController v1";
	}
	
	@RequestMapping("/hello")
	public String hello() {
		return "Welcome to AWS example";
	}
	
	@RequestMapping("/summaryreport")
	public void getSummaryReport(HttpServletResponse response) throws FileNotFoundException, IOException{
		 response.setContentType("text/csv");
		 response.setHeader("Content-Disposition", "attachment; filename=DATA.csv");
		 response.getOutputStream().write(feeCalculatorService.getSummaryReport().getBytes());
		 response.getOutputStream().flush();
	}
}
