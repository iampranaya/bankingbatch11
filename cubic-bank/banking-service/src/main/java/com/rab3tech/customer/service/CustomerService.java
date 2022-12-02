package com.rab3tech.customer.service;

import com.rab3tech.vo.CustomerVO;

public interface CustomerService {

	CustomerVO createAccount(CustomerVO customerVO);

	String updateProfile(CustomerVO customer);

	CustomerVO getCustomerData(String email);

}
