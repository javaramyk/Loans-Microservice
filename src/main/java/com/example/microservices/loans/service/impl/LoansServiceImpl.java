package com.example.microservices.loans.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservices.loans.constants.LoansConstants;
import com.example.microservices.loans.dto.LoansDto;
import com.example.microservices.loans.entity.Loans;
import com.example.microservices.loans.exception.LoanAlreadyExistsException;
import com.example.microservices.loans.exception.ResourceNotFoundException;
import com.example.microservices.loans.mapper.LoansMapper;
import com.example.microservices.loans.repository.LoansRepository;
import com.example.microservices.loans.service.ILoansService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

	@Autowired
	private LoansRepository loansRepository;
	
	@Override
	public void createLoan(String mobileNumber) {
		// TODO Auto-generated method stub
       Optional<Loans> optionalLoans=loansRepository.findByMobileNumber(mobileNumber);
       if(optionalLoans.isPresent()) {
           throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+mobileNumber);
       }
       loansRepository.save(createNewLoan(mobileNumber));
	}

	private Loans createNewLoan(String mobileNumber) {
		// TODO Auto-generated method stub
		  Loans newLoan = new Loans();
	        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
	        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
	        newLoan.setMobileNumber(mobileNumber);
	        newLoan.setLoanType(LoansConstants.HOME_LOAN);
	        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
	        newLoan.setAmountPaid(0);
	        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
	        return newLoan;
	}

	@Override
	public LoansDto fetchLoan(String mobileNumber) {
		// TODO Auto-generated method stub
		Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(()->new ResourceNotFoundException("Loan","mobileNumber",mobileNumber));
		
		return LoansMapper.mapToLoansDto(loans, new LoansDto());
	}

	@Override
	public boolean updateLoan(LoansDto loansDto) {
		// TODO Auto-generated method stub
		Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);
        return  true;
	}

	@Override
	public boolean deleteLoan(String mobileNumber) {
		// TODO Auto-generated method stub
		 Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
	                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
	        );
	        loansRepository.deleteById(loans.getLoanId());
	        return true;
	}

}
