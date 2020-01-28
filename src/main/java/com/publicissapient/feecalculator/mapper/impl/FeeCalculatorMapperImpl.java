package com.publicissapient.feecalculator.mapper.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.publicissapient.feecalculator.mapper.FeeCalculatorMapper;
import com.publicissapient.feecalculator.vo.InputVO;
import com.publicissapient.feecalculator.vo.SummaryReportVO;

@Service
public class FeeCalculatorMapperImpl implements FeeCalculatorMapper {

	@Override
	public List<InputVO> getTransactionVOFromWorkBook(File csvFile) throws FileNotFoundException, IOException {
		List<InputVO> inputVOList = new ArrayList<InputVO>();
		BufferedReader csvReader = new BufferedReader(new FileReader("C:\\Users\\Anjali\\Input data.csv"));
		String row;
		int i = 0;
		while ((row = csvReader.readLine()) != null) {
			String[] data = row.split(",");
			if (data != null && i!=0) {
				InputVO inputVO = new InputVO();
				if (data[0] != null)
					inputVO.setExternalTransactionId(data[0]);
				if (data[1] != null)
					inputVO.setClientId(data[1]);
				if (data[2] != null)
					inputVO.setSecurityId(data[2]);
				if (data[3] != null)
					inputVO.setTransactionType(data[3]);
				if (data[4] != null) {
					try {
						inputVO.setTransactionDate(new SimpleDateFormat("MM/dd/yyyy").parse(data[4]));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (data[5] != null)
					inputVO.setMarkteValue(data[5]);
				if (data[6] != null)
					inputVO.setPriorityFlag(data[6]);
				inputVOList.add(inputVO);
			}
			i++;
		}
		csvReader.close();
		return inputVOList;
	}

	@Override
	public SummaryReportVO getSummaryReportFromInputAndFee(InputVO inputVO, BigDecimal fee) {
		if(inputVO != null){
			SummaryReportVO summaryReportVO = new SummaryReportVO();
			summaryReportVO.setClientId(inputVO.getClientId());
			summaryReportVO.setPriorityFlag(inputVO.getPriorityFlag());
			summaryReportVO.setProcesingFee(fee);
			summaryReportVO.setTransactionDate(inputVO.getTransactionDate());
			summaryReportVO.setTransactionType(inputVO.getTransactionType());
			return summaryReportVO;
		}
		return null;
	}

	@Override
	public String getSummaryReportFromVO(SummaryReportVO summaryReportVO) {
		if(summaryReportVO != null){
			StringBuilder summaryReport = new StringBuilder();
			summaryReport.append(summaryReportVO.getClientId()+",");
			summaryReport.append(summaryReportVO.getTransactionType()+",");
			summaryReport.append(new SimpleDateFormat("MM/dd/yyyy").format(summaryReportVO.getTransactionDate())+",");
			summaryReport.append(summaryReportVO.getPriorityFlag()+",");
			summaryReport.append(summaryReportVO.getProcesingFee()+"\n");
			return summaryReport.toString();
		}
		return null;
	}
}
