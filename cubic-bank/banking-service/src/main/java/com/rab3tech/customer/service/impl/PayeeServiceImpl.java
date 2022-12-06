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
import com.rab3tech.customer.service.PayeeService;
import com.rab3tech.dao.entity.Customer;
import com.rab3tech.dao.entity.CustomerAccountInfo;
import com.rab3tech.dao.entity.PayeeInfo;
import com.rab3tech.vo.PayeeInfoVO;


@Service//
@Transactional
public class PayeeServiceImpl implements PayeeService{
	@Autowired
	PayeeInfoRepository payeeInfoRepository;
	
	@Autowired
	CustomerAccountInfoRepository accountRepo ;
	
	@Autowired
	CustomerRepository customerRepo;
	
	@Override
	public String addPayee(PayeeInfoVO payeeVO) {
		//check sender has valid account or not
				Optional<CustomerAccountInfo> senderAccount = accountRepo.findByCustomerId(payeeVO.getCustomerId());
				if(!senderAccount.isPresent()) {
					return "Payee cannot be added as sender is not having valid account";
				}else {
					String accType = senderAccount.get().getAccountType();
					if(!accType.equals("SAVING")) {
						return "Payee cannot be added as sender is not having valid SAVING account";
					}
				}
				//check not adding himself as payee
				if(senderAccount.get().getAccountNumber().equals(payeeVO.getPayeeAccountNo())) {
					return "Sender cannot add himself as receiver";
				}
				//check receiver has valid account or not
				Optional<CustomerAccountInfo> receiverAccount = accountRepo.findByAccountNumber(payeeVO.getPayeeAccountNo());
				if(!receiverAccount.isPresent()) {
					return "Payee cannot be added as receiver is not having valid account";
				}else {
					if(!receiverAccount.get().getAccountType().equals("SAVING")) {
						return "Payee cannot be added as receiver is not having valid SAVING account";
					}
				}
				
				
				//check receiver's name is valid or not ?
				Optional<Customer> receiver = customerRepo.findByEmail(receiverAccount.get().getCustomerId());
				
				if(!receiver.get().getName().equalsIgnoreCase(payeeVO.getPayeeName())) {
					return "Payee cannot be added as receiver is not having valid NAME";
				}
				
				//check sender is not adding duplicate receiver
				Optional<PayeeInfo> duplicatePayee = payeeInfoRepository.findByCustomerIdAndPayeeAccountNo(payeeVO.getCustomerId(), payeeVO.getPayeeAccountNo());
				if(duplicatePayee.isPresent()) {
					return "Payee cannot be added as it's already preset for sender";
				}
				
				PayeeInfo entity = new PayeeInfo();
				BeanUtils.copyProperties(payeeVO, entity);
				entity.setDoe(new Timestamp(new Date().getTime()));
				//entity.setDom(new Timestamp(new Date().getTime()));
				entity.setStatus("Approved");
				payeeInfoRepository.save(entity);
				return "payee added sucessfully";
			}
	
	@Override
	public List<PayeeInfoVO> findAllPayee(String customerId) {
		List<PayeeInfoVO> voList = new ArrayList<PayeeInfoVO>();
		List<PayeeInfo> payeeEntity = payeeInfoRepository.findByCustomerId(customerId);
		
		for(PayeeInfo entity:payeeEntity) {    
			PayeeInfoVO vo = new PayeeInfoVO();
			BeanUtils.copyProperties(entity, vo);
			voList.add(vo);
		}
		
		
		return voList;
	}

}
