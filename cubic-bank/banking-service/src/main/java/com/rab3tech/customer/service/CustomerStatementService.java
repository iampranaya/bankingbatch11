package com.rab3tech.customer.service;

import java.util.List;

import com.rab3tech.vo.StatementVO;



public interface CustomerStatementService {

	List<StatementVO> showCustomerStatement(String username);

}
