package com.publicissapient.feecalculator.vo;

import java.math.BigDecimal;
import java.util.Date;

public class SummaryReportVO {
	String clientId;
	String transactionType;
	Date transactionDate;
	String priorityFlag;
	BigDecimal procesingFee;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getPriorityFlag() {
		return priorityFlag;
	}
	public void setPriorityFlag(String priorityFlag) {
		this.priorityFlag = priorityFlag;
	}
	public BigDecimal getProcesingFee() {
		return procesingFee;
	}
	public void setProcesingFee(BigDecimal procesingFee) {
		this.procesingFee = procesingFee;
	}
	
}