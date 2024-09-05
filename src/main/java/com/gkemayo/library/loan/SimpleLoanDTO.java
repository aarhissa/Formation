package com.gkemayo.library.loan;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(defaultValue = "Simple Loan Model")
public class SimpleLoanDTO {
	
	@Schema(defaultValue = "Book id concerned by the loan")
	private Integer bookId;
	
	@Schema(defaultValue = "Customer id concerned by the loan")
	private Integer customerId;
	
	@Schema(defaultValue = "Loan begining date")
	private LocalDate beginDate;
	
	@Schema(defaultValue = "Loan ending date")
	private LocalDate endDate;

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public LocalDate getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
}
