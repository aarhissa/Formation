package com.gkemayo.library.loan;

import java.time.LocalDate;

import com.gkemayo.library.book.BookDTO;
import com.gkemayo.library.customer.CustomerDTO;

import io.swagger.v3.oas.annotations.media.Schema;



@Schema(defaultValue = "Loan Model")
public class LoanDTO implements Comparable<LoanDTO> {

	@Schema(defaultValue = "Book concerned by the loan")
	private BookDTO bookDTO = new BookDTO();

	@Schema(defaultValue = "Customer concerned by the loan")
	private CustomerDTO customerDTO = new CustomerDTO();

	@Schema(defaultValue = "Loan begining date")
	private LocalDate loanBeginDate;

	@Schema(defaultValue = "Loan ending date")
	private LocalDate loanEndDate;

	public LocalDate getLoanBeginDate() {
		return loanBeginDate;
	}

	public void setLoanBeginDate(LocalDate loanBeginDate) {
		this.loanBeginDate = loanBeginDate;
	}

	public LocalDate getLoanEndDate() {
		return loanEndDate;
	}

	public void setLoanEndDate(LocalDate loanEndDate) {
		this.loanEndDate = loanEndDate;
	}

	public BookDTO getBookDTO() {
		return bookDTO;
	}

	public void setBookDTO(BookDTO bookDTO) {
		this.bookDTO = bookDTO;
	}

	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	@Override
	public int compareTo(LoanDTO o) {
		// ordre decroissant
		return o.getLoanBeginDate().compareTo(this.loanBeginDate);
	}

}
