package com.rab3tech.customer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rab3tech.customer.dao.repository.CustomerAccountInfoRepository;
import com.rab3tech.customer.dao.repository.CustomerRepository;
import com.rab3tech.customer.dao.repository.TransactionRepository;
import com.rab3tech.customer.service.CustomerStatementService;
import com.rab3tech.dao.entity.Customer;
import com.rab3tech.dao.entity.CustomerAccountInfo;
import com.rab3tech.dao.entity.Transaction;
import com.rab3tech.vo.StatementVO;

@Service
@Transactional
public class CustomerStatementServiceImpl implements CustomerStatementService {

	@Autowired
	CustomerAccountInfoRepository customerAccountInfoRepository;

	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public List<StatementVO> showCustomerStatement(String username) {
		List<StatementVO> statementVO = new ArrayList<StatementVO>();

		Optional<CustomerAccountInfo> customerAccountInformation = customerAccountInfoRepository.findByCustomerId(username);
		Optional<Customer> customerInfo = customerRepository.findByEmail(username);
		
		float balance = customerAccountInformation.get().getAvBalance();
		
		// vo.setAccountNumber(customerAccountInformation.get().getAccountNumber());
		if (customerAccountInformation.isPresent()) {
			List<Transaction> transaction = transactionRepository.findByCustomerIdOrPayeeAccountNo(username,customerAccountInformation.get().getAccountNumber());

			for (Transaction trans : transaction) {
				StatementVO vo = new StatementVO();	
				vo.setTavBalance(balance);
				BeanUtils.copyProperties(trans, vo);				
				if (trans.getCustomerId().equals(username)) {
					vo.setAccountType("Debit");
					vo.setAccountNumber(trans.getPayeeAccountNo());
					vo.setName(customerInfo.get().getName());
					
					} else {
					vo.setAccountType("CREDIT");
					vo.setAccountNumber(customerAccountInformation.get().getAccountNumber());
					Optional<Customer> senderInfo = customerRepository.findByEmail(trans.getCustomerId());
					vo.setName(senderInfo.get().getName());
					}
				statementVO.add(vo);
			}

		}
		return statementVO;
	}

}
