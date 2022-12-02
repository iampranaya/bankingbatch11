package com.rab3tech.customer.service;

import java.util.List;

import com.rab3tech.vo.TransactionVO;

public interface TransactionService {

	 String fundTransfer(TransactionVO transaction);

	List<TransactionVO> showData();



	
}
