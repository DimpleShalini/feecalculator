package com.publicissapient.feecalculator.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.publicissapient.feecalculator.mapper.FeeCalculatorMapper;
import com.publicissapient.feecalculator.service.FeeCalculatorService;
import com.publicissapient.feecalculator.utility.FeeCalculatorConstants;
import com.publicissapient.feecalculator.vo.InputVO;
import com.publicissapient.feecalculator.vo.SummaryReportVO;

@Service
public class FeeCalculatorServiceImpl implements FeeCalculatorService {

	@Autowired
	FeeCalculatorMapper feeCalculatorMapper;

	@Override
	public String getSummaryReport() throws FileNotFoundException, IOException {
		File csvFile = new File("C:\\Users\\Anjali\\Input data.csv");
		List<InputVO> inputVOList = new ArrayList<InputVO>();
		if (csvFile != null)
			inputVOList = feeCalculatorMapper.getTransactionVOFromWorkBook(csvFile);
		List<SummaryReportVO> summaryReportVOList = calculateProcessingFee(inputVOList);
		sortReport(summaryReportVOList);
		return getReportAsCSV(summaryReportVOList);
	}

	private List<SummaryReportVO> calculateProcessingFee(List<InputVO> inputVOList) {
		List<SummaryReportVO> summaryReportVOList = new ArrayList<SummaryReportVO>();
		for (InputVO inputVO : inputVOList) {
			BigDecimal processingFee = new BigDecimal(0);
			if (inputVO != null) {
				if(FeeCalculatorConstants.BUY.equalsIgnoreCase(inputVO.getTransactionType())){
					if(inputVOList.stream().filter(s -> s.getTransactionDate().equals(inputVO.getTransactionDate())).
						filter(s -> s.getClientId().equals(inputVO.getClientId())).
						filter(s -> s.getSecurityId().equals(inputVO.getSecurityId())).
						filter(s->FeeCalculatorConstants.SELL.equalsIgnoreCase(s.getTransactionType())).count() > 0){
						processingFee = processingFee.add(new BigDecimal(10));
					}
				} else if(FeeCalculatorConstants.SELL.equalsIgnoreCase(inputVO.getTransactionType())){
					if(inputVOList.stream().filter(s -> s.getTransactionDate().equals(inputVO.getTransactionDate())).
							filter(s -> s.getClientId().equals(inputVO.getClientId())).
							filter(s -> s.getSecurityId().equals(inputVO.getSecurityId())).
							filter(s->FeeCalculatorConstants.BUY.equalsIgnoreCase(s.getTransactionType())).count() > 0){
							processingFee = processingFee.add(new BigDecimal(10));
						}
					}
				if (FeeCalculatorConstants.HIGH_PRIORITY.equalsIgnoreCase(inputVO.getPriorityFlag())) {
					processingFee = processingFee.add(new BigDecimal(500));
				} else {
					if (FeeCalculatorConstants.DOLLAR_100.contains(inputVO.getTransactionType())) {
						processingFee = processingFee.add(new BigDecimal(100));
					} else if (FeeCalculatorConstants.DOLLAR_50.contains(inputVO.getTransactionType())) {
						processingFee = processingFee.add(new BigDecimal(50));
					}
				}
				summaryReportVOList.add(feeCalculatorMapper.getSummaryReportFromInputAndFee(inputVO, processingFee));
			}
		}
		return summaryReportVOList;
	}

	private String getReportAsCSV(List<SummaryReportVO> summaryReportVOList) {
		StringBuilder summaryReport = new StringBuilder();
		summaryReport.append(FeeCalculatorConstants.REPORT_HEADER);
		for (SummaryReportVO summaryReportVO : summaryReportVOList) {
			summaryReport.append(feeCalculatorMapper.getSummaryReportFromVO(summaryReportVO));
		}
		return summaryReport.toString();
	}
	
	private void sortReport(List<SummaryReportVO> summaryReportVOList) {
		summaryReportVOList.sort(new Comparator<SummaryReportVO>() {
			@Override
			public int compare(SummaryReportVO o1, SummaryReportVO o2) {
				return o1.getPriorityFlag().compareTo(o2.getPriorityFlag());
			}
		});
		summaryReportVOList.sort(new Comparator<SummaryReportVO>() {
			@Override
			public int compare(SummaryReportVO o1, SummaryReportVO o2) {
				return o1.getTransactionDate().compareTo(o2.getTransactionDate());
			}
		});
		summaryReportVOList.sort(new Comparator<SummaryReportVO>() {
			@Override
			public int compare(SummaryReportVO o1, SummaryReportVO o2) {
				return o1.getTransactionType().compareTo(o2.getTransactionType());
			}
		});
		summaryReportVOList.sort(new Comparator<SummaryReportVO>() {
			@Override
			public int compare(SummaryReportVO o1, SummaryReportVO o2) {
				return o1.getClientId().compareTo(o2.getClientId());
			}
		});
	}
}
