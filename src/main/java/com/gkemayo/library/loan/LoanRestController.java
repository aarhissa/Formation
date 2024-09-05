package com.gkemayo.library.loan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.gkemayo.library.book.Book;
import com.gkemayo.library.customer.Customer;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;


@RestController
@RequestMapping("/rest/loan/api")
@Tag(description = "Loan Rest Controller: contains all operations for managing loans", name = "Loan")
public class LoanRestController {

	public static final Logger LOGGER = LoggerFactory.getLogger(LoanRestController.class);

	@Autowired
	private LoanServiceImpl loanService;

	/**
	 * Retourne l'historique des prêts en cours dans la bibliothèque jusqu'à une certaine date maximale. 
	 * @param maxEndDateStr
	 * @return
	 */
	@GetMapping("/maxEndDate")
	@Operation(summary="List loans realized before the indicated date")
	@ApiResponse(responseCode = "200", description = "Ok: successfully listed")
	public ResponseEntity<List<LoanDTO>> searchAllBooksLoanBeforeThisDate(@RequestParam("date") String  maxEndDateStr) {
		List<Loan> loans = loanService.findAllLoansByEndDateBefore(LocalDate.parse(maxEndDateStr));
		// on retire tous les élts null que peut contenir cette liste => pour éviter les NPE par la suite
		loans.removeAll(Collections.singleton(null));
		List<LoanDTO> loanInfosDtos = mapLoanDtosFromLoans(loans);
		return new ResponseEntity<List<LoanDTO>>(loanInfosDtos, HttpStatus.OK);
	}
	
	/**
	 * Retourne la liste des prêts en cours d'un client. 
	 * @param email
	 * @return
	 */
	@GetMapping("/customerLoans")
	@Operation(summary="List loans realized before the indicated date")
	@ApiResponse(responseCode = "200", description = "Ok: successfully listed")
	public ResponseEntity<List<LoanDTO>> searchAllOpenedLoansOfThisCustomer(@RequestParam("email") String email) {
		List<Loan> loans = loanService.getAllOpenLoansOfThisCustomer(email, LoanStatus.OPEN);
		// on retire tous les élts null que peut contenir cette liste => pour éviter les NPE par la suite
		loans.removeAll(Collections.singleton(null));
		List<LoanDTO> loanInfosDtos = mapLoanDtosFromLoans(loans);
		return new ResponseEntity<List<LoanDTO>>(loanInfosDtos, HttpStatus.OK);
	}
	
	/**
	 * Ajoute un nouveau prêt dans la base de données H2.
	 * @param simpleLoanDTORequest
	 * @param uriComponentBuilder
	 * @return
	 */
	@PostMapping("/addLoan")
	@Operation(summary = "Add a new Loan in the Library")
	@ApiResponses(value = { @ApiResponse(responseCode = "409", description = "Conflict: the loan already exist"),
			@ApiResponse(responseCode = "201", description = "Created: the loan is successfully inserted"),
			@ApiResponse(responseCode = "304", description = "Not Modified: the loan is unsuccessfully inserted") })
	public ResponseEntity<Boolean> createNewLoan(@RequestBody SimpleLoanDTO simpleLoanDTORequest,
			UriComponentsBuilder uriComponentBuilder) {
		boolean isLoanExists = loanService.checkIfLoanExists(simpleLoanDTORequest);
		if (isLoanExists) {
			return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);
		}
		Loan LoanRequest = mapSimpleLoanDTOToLoan(simpleLoanDTORequest);
		Loan loan = loanService.saveLoan(LoanRequest);
		if (loan != null) {
			return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.NOT_MODIFIED);

	}
	
	/**
	 * Cloture le prêt de livre d'un client.
	 * @param simpleLoanDTORequest
	 * @param uriComponentBuilder
	 * @return
	 */
	@PostMapping("/closeLoan")
	@Operation(summary = "Marks as close a Loan in the Library")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "No Content: no loan founded"),
			@ApiResponse(responseCode = "200", description = "Ok: the loan is successfully closed"),
			@ApiResponse(responseCode = "304", description = "Not Modified: the loan is unsuccessfully closed") })
	public ResponseEntity<Boolean> closeLoan(@RequestBody SimpleLoanDTO simpleLoanDTORequest,
			UriComponentsBuilder uriComponentBuilder) {
		Loan existingLoan = loanService.getOpenedLoan(simpleLoanDTORequest);
		if (existingLoan == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.NO_CONTENT);
		}
		existingLoan.setStatus(LoanStatus.CLOSE);
		Loan loan = loanService.saveLoan(existingLoan);
		if (loan != null) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(HttpStatus.NOT_MODIFIED);

	}

	/**
	 * Transforme a Loan List to LoanDTO List.
	 * 
	 * @param loans
	 * @return
	 */
	private List<LoanDTO> mapLoanDtosFromLoans(List<Loan> loans) {

		Function<Loan, LoanDTO> mapperFunction = (loan) -> {
			// dans loanDTO on ajoute que les données nécessaires
			LoanDTO loanDTO = new LoanDTO();
			loanDTO.getBookDTO().setId(loan.getPk().getBook().getId());
			loanDTO.getBookDTO().setIsbn(loan.getPk().getBook().getIsbn());
			loanDTO.getBookDTO().setTitle(loan.getPk().getBook().getTitle());

			loanDTO.getCustomerDTO().setId(loan.getPk().getCustomer().getId());
			loanDTO.getCustomerDTO().setFirstName(loan.getPk().getCustomer().getFirstName());
			loanDTO.getCustomerDTO().setLastName(loan.getPk().getCustomer().getLastName());
			loanDTO.getCustomerDTO().setEmail(loan.getPk().getCustomer().getEmail());

			loanDTO.setLoanBeginDate(loan.getBeginDate());
			loanDTO.setLoanEndDate(loan.getEndDate());
			return loanDTO;
		};

		if (!CollectionUtils.isEmpty(loans)) {
			return loans.stream().map(mapperFunction).sorted().collect(Collectors.toList());
		}
		return null;
	}
	
	/**
	 * Transforme un SimpleLoanDTO en Loan avec les données minimalistes nécessaires
	 * 
	 * @param loanDTORequest
	 * @return
	 */
	private Loan mapSimpleLoanDTOToLoan(SimpleLoanDTO simpleLoanDTO) {
		Loan loan = new Loan();
		Book book = new Book();
		book.setId(simpleLoanDTO.getBookId());
		Customer customer = new Customer();
		customer.setId(simpleLoanDTO.getCustomerId());
		LoanId loanId = new LoanId(book, customer);
		loan.setPk(loanId);
		loan.setBeginDate(simpleLoanDTO.getBeginDate());
		loan.setEndDate(simpleLoanDTO.getEndDate());
		loan.setStatus(LoanStatus.OPEN);
		return loan;
	}

}
