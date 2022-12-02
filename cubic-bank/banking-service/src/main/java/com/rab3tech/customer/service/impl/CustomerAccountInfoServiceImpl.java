package com.rab3tech.customer.service.impl;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rab3tech.customer.dao.repository.CustomerAccountEnquiryRepository;
import com.rab3tech.customer.dao.repository.CustomerAccountInfoRepository;
import com.rab3tech.customer.service.CustomerAccountInfoService;
import com.rab3tech.dao.entity.CustomerAccountInfo;
import com.rab3tech.dao.entity.CustomerSaving;
import com.rab3tech.vo.CustomerAccountInfoVO;

@Service
@Transactional
public class CustomerAccountInfoServiceImpl implements CustomerAccountInfoService{

	

	@Autowired
	CustomerAccountInfoRepository accountRepository;
	
	@Autowired
	CustomerAccountEnquiryRepository customerAccountEnquiryRepository;
	
	@Override
	public CustomerAccountInfoVO createAccount(String logonId) {
		//check account is preset or not ????
		Optional<CustomerAccountInfo> customerAccount = accountRepository.findByCustomerId(logonId);
		CustomerAccountInfo account = new CustomerAccountInfo();
		CustomerAccountInfoVO vo = new CustomerAccountInfoVO();
		if(!(customerAccount.isPresent())) {
			CustomerAccountInfo entity = new CustomerAccountInfo(); 
			//set whole entity
			entity.setCustomerId(logonId);
			entity.setCurrency("USD");
			entity.setAvBalance(1000);
			entity.setTavBalance(1000);
			entity.setStatusAsOf(new Date());
			//Call customer_saving_enquiry_tbl and get below informations 
			CustomerSaving customerSaving = customerAccountEnquiryRepository.findByEmail(logonId).get();
			entity.setAccountNumber(customerSaving.getAppref()); 
			entity.setBranch(customerSaving.getLocation()); //TBD
			entity.setAccountType(customerSaving.getAccType().getName()); //TBD
			account = accountRepository.save(entity);
			vo.setMessage("Congrats your new account has been created");
		}else {
			account = customerAccount.get();
			vo.setMessage("You already have existing account below are the details");
		}
		
		
		
		ModelMapper modelMapper = new ModelMapper();
		vo  = modelMapper.map(account, CustomerAccountInfoVO .class);


		
		//BeanUtils.copyProperties(account, vo);
		return vo;
		
	}
	
}
