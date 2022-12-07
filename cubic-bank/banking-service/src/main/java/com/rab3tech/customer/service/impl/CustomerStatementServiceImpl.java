package com.rab3tech.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rab3tech.customer.dao.repository.CustomerStatementRepository;
import com.rab3tech.customer.service.CustomerStatementService;
import com.rab3tech.vo.StatementVO;

@Service
@Transactional
public class CustomerStatementServiceImpl implements CustomerStatementService {
	
	@Autowired
	 CustomerStatementRepository CustomerStatementRepository;

	@Override
	public List<StatementVO> showCustomerStatement(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
