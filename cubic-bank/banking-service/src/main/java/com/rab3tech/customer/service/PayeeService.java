package com.rab3tech.customer.service;

import java.util.List;

import com.rab3tech.vo.PayeeInfoVO;

public interface PayeeService {

	String addPayee(PayeeInfoVO payeeVO);

	List<PayeeInfoVO> findAllPayee(String customerId);

}
