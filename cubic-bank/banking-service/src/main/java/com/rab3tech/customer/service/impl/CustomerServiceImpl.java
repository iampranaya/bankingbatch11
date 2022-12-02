package com.rab3tech.customer.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rab3tech.admin.dao.repository.MagicCustomerRepository;
import com.rab3tech.customer.dao.repository.CustomerQuestionsAnsRepository;
import com.rab3tech.customer.dao.repository.RoleRepository;
import com.rab3tech.customer.service.CustomerService;
import com.rab3tech.dao.entity.Customer;
import com.rab3tech.dao.entity.CustomerQuestionAnswer;
import com.rab3tech.dao.entity.Login;
import com.rab3tech.dao.entity.Role;
import com.rab3tech.email.service.EmailService;
import com.rab3tech.utils.PasswordGenerator;
import com.rab3tech.vo.CustomerVO;

@Service
@Transactional
public class CustomerServiceImpl implements  CustomerService{
	
	@Autowired
	private MagicCustomerRepository customerRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private CustomerQuestionsAnsRepository customerQuestionsAnsRepository;
	
	
	@Override
	public CustomerVO createAccount(CustomerVO customerVO) {
		Customer pcustomer = new Customer();
		BeanUtils.copyProperties(customerVO, pcustomer);
		Login login = new Login();
		login.setNoOfAttempt(3);
		login.setLoginid(customerVO.getEmail());
		login.setName(customerVO.getName());
		String genPassword=PasswordGenerator.generateRandomPassword(8);
		customerVO.setPassword(genPassword);
		login.setPassword(bCryptPasswordEncoder.encode(genPassword));
		login.setToken(customerVO.getToken());
		login.setLocked("no");
		
		Role entity=roleRepository.findById(3).get();
		Set<Role> roles=new HashSet<>();
		roles.add(entity);
		//setting roles inside login
		login.setRoles(roles);
		//setting login inside
		pcustomer.setLogin(login);
		Customer dcustomer=customerRepository.save(pcustomer);
		customerVO.setId(dcustomer.getId());
		customerVO.setUserid(customerVO.getUserid());
		return customerVO;
	}
	

	@Override
	public CustomerVO getCustomerData(String email) {
		
		CustomerVO vo = new CustomerVO();//blank object
		Optional<Customer>  customer = customerRepository.findByEmail(email);
		BeanUtils.copyProperties(customer.get(), vo);
		//get Q&A data and set in vo
		List<CustomerQuestionAnswer> customerQuestionAnswers=customerQuestionsAnsRepository.findQuestionAnswer(email);
		vo.setQuestion1(customerQuestionAnswers.get(0).getQuestion());
		vo.setAnswer1(customerQuestionAnswers.get(0).getAnswer());
		
		vo.setQuestion2(customerQuestionAnswers.get(1).getQuestion());
		vo.setAnswer2(customerQuestionAnswers.get(1).getAnswer());
		return vo;
	}
	
	

	@Override
	public String updateProfile(CustomerVO customervo) {
		//Customer customerEntity = new Customer();
			Customer customerEntity = customerRepository.findByEmail(customervo.getEmail()).get();
			//String[] ignoreProp = {"photoName","email","login"};
			String[] ignoreProp = {"photoName","email","id"};
			BeanUtils.copyProperties(customervo,customerEntity, ignoreProp);
			customerEntity.setDom(new Timestamp(new Date().getTime()));
			List<CustomerQuestionAnswer> customerQuestionAnswers = customerQuestionsAnsRepository.findQuestionAnswer(customervo.getEmail());
			CustomerQuestionAnswer q1 = customerQuestionAnswers.get(0);
			q1.setAnswer(customervo.getAnswer1());
			q1.setDom(new Timestamp(new Date().getTime()));
			customerQuestionsAnsRepository.save(q1);
			
			CustomerQuestionAnswer q2 = customerQuestionAnswers.get(1);
			q2.setAnswer(customervo.getAnswer2());
			q2.setDom(new Timestamp(new Date().getTime()));
			customerQuestionsAnsRepository.save(q2);	
			
			customerRepository.save(customerEntity);
			return "Profile updated sucessfully";
		}

}
