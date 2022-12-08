package com.rab3tech.customer.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rab3tech.customer.dao.repository.CustomerAccountInfoRepository;
import com.rab3tech.customer.dao.repository.CustomerRepository;
import com.rab3tech.customer.dao.repository.PayeeInfoRepository;
import com.rab3tech.customer.dao.repository.TransactionRepository;
import com.rab3tech.customer.service.TransactionService;
import com.rab3tech.dao.entity.Customer;
import com.rab3tech.dao.entity.CustomerAccountInfo;
import com.rab3tech.dao.entity.PayeeInfo;
import com.rab3tech.dao.entity.Transaction;
import com.rab3tech.vo.TransactionVO;
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	CustomerAccountInfoRepository accountRepo ;
	
	@Autowired
	TransactionRepository transactionRepository;
		
	@Override
	public String fundTransfer(TransactionVO vo) {

		//senderAccount validation
				Optional<CustomerAccountInfo> senderAccount = accountRepo.findByCustomerId(vo.getCustomerId());
				if(!senderAccount.isPresent()) {
					return "Amont cannot be transferred sender is not having valid account";
				}else {
					String accType = senderAccount.get().getAccountType();
					if(!accType.equals("SAVING")) {
						return "Amont cannot be transferred  as sender is not having valid SAVING account";
					}
				}
				CustomerAccountInfo sender = senderAccount.get();
				//Fund validation
				float senderBalance = sender.getAvBalance();
				if(senderBalance < vo.getAmount()) {
					return "Amont cannot be transferred  as sender is not having sufficient funds";
				}
				//receiverAccount validation
				Optional<CustomerAccountInfo> receiverAccount = accountRepo.findByAccountNumber(vo.getPayeeAccountNo());
				if(!receiverAccount.isPresent()) {
					return "Amont cannot be transferred  as receiver is not having valid account";
				}else {
					if(!receiverAccount.get().getAccountType().equals("SAVING")) {
						return "Amont cannot be transferred  as receiver is not having valid SAVING account";
					}
				}
				CustomerAccountInfo receiver = receiverAccount.get();
				//deduct sender's amount
				sender.setAvBalance(senderBalance - vo.getAmount());
				sender.setTavBalance(senderBalance - vo.getAmount());
				sender.setStatusAsOf(new Date());
				
				//Add receiver's amount
				receiver.setAvBalance(receiver.getAvBalance() + vo.getAmount());
				receiver.setTavBalance(receiver.getAvBalance() + vo.getAmount());
				receiver.setStatusAsOf(new Date());
				
				Transaction transaction = new Transaction();
				BeanUtils.copyProperties(vo, transaction);
					
				transaction.setDoe(new Timestamp(new Date().getTime()));
				//transaction. setPayeeNickName(transactionData.getPayeeNickName().get());
				transactionRepository.save(transaction);
				return "Amount has been transferred sucessfully";
			}

	@Override
	public List<TransactionVO> showData() {
		List<Transaction> transactions = transactionRepository.findAll();
		List<TransactionVO> transactionVOs = new ArrayList<>();
		for(Transaction element : transactions) {
			TransactionVO transactionVO  = new TransactionVO();
			BeanUtils.copyProperties(element, transactionVO);
			transactionVOs.add(transactionVO);
		}
		
		
		
		return transactionVOs;
	}
	}


