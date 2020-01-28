package com.publicissapient.feecalculator.mapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.publicissapient.feecalculator.vo.InputVO;
import com.publicissapient.feecalculator.vo.SummaryReportVO;

public interface FeeCalculatorMapper {
	List<InputVO> getTransactionVOFromWorkBook(File csvFile) throws FileNotFoundException, IOException;
	SummaryReportVO getSummaryReportFromInputAndFee(InputVO inputVO, BigDecimal fee);
	String getSummaryReportFromVO(SummaryReportVO summaryReportVO);
}
