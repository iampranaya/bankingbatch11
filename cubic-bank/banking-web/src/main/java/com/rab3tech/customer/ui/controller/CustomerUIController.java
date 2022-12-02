package com.rab3tech.customer.ui.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rab3tech.customer.service.CustomerAccountInfoService;
import com.rab3tech.customer.service.CustomerService;
import com.rab3tech.customer.service.LoginService;
import com.rab3tech.customer.service.PayeeService;
import com.rab3tech.customer.service.TransactionService;

import com.rab3tech.customer.service.impl.CustomerEnquiryService;
import com.rab3tech.customer.service.impl.SecurityQuestionService;
import com.rab3tech.email.service.EmailService;
import com.rab3tech.vo.ChangePasswordVO;
import com.rab3tech.vo.CustomerAccountInfoVO;
import com.rab3tech.vo.CustomerSavingVO;
import com.rab3tech.vo.CustomerSecurityQueAnsVO;
import com.rab3tech.vo.CustomerVO;
import com.rab3tech.vo.EmailVO;
import com.rab3tech.vo.LoginVO;
import com.rab3tech.vo.PayeeInfoVO;
import com.rab3tech.vo.TransactionVO;

/**
 * 
 * @author nagendra
 * This class for customer GUI
 *
 */
@Controller
public class CustomerUIController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerUIController.class);

	@Autowired
	private CustomerEnquiryService customerEnquiryService;

	
	@Autowired
	private SecurityQuestionService securityQuestionService;
	
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
   private LoginService loginService;	
	

	@Autowired
	private CustomerAccountInfoService accountService;
	
	@Autowired
	private PayeeService payeeService;
	
	@PostMapping("/customer/changePassword")
	public String saveCustomerQuestions(@ModelAttribute ChangePasswordVO changePasswordVO, Model model,HttpSession session) {
		LoginVO  loginVO2=(LoginVO)session.getAttribute("userSessionVO");
		String loginid=loginVO2.getUsername();
		changePasswordVO.setLoginid(loginid);
		String viewName ="customer/dashboard";
		boolean status=loginService.checkPasswordValid(loginid,changePasswordVO.getCurrentPassword());
		if(status) {
			if(changePasswordVO.getNewPassword().equals(changePasswordVO.getConfirmPassword())) {
				 viewName ="customer/dashboard";
				 loginService.changePassword(changePasswordVO);
			}else {
				model.addAttribute("error","Sorry , your new password and confirm passwords are not same!");
				return "customer/login";	//login.html	
			}
		}else {
			model.addAttribute("error","Sorry , your username and password are not valid!");
			return "customer/login";	//login.html	
		}
		return viewName;
	}
	
	@PostMapping("/customer/securityQuestion")
	public String saveCustomerQuestions(@ModelAttribute("customerSecurityQueAnsVO") CustomerSecurityQueAnsVO customerSecurityQueAnsVO, Model model,HttpSession session) {
		LoginVO  loginVO2=(LoginVO)session.getAttribute("userSessionVO");
		String loginid=loginVO2.getUsername();
		customerSecurityQueAnsVO.setLoginid(loginid);
		securityQuestionService.save(customerSecurityQueAnsVO);
		//
		return "customer/chagePassword";
	}

	// http://localhost:444/customer/account/registration?cuid=1585a34b5277-dab2-475a-b7b4-042e032e8121603186515
	@GetMapping("/customer/account/registration")
	public String showCustomerRegistrationPage(@RequestParam String cuid, Model model) {

		logger.debug("cuid = " + cuid);
		Optional<CustomerSavingVO> optional = customerEnquiryService.findCustomerEnquiryByUuid(cuid);
		CustomerVO customerVO = new CustomerVO();

		if (!optional.isPresent()) {
			return "customer/error";
		} else {
			// model is used to carry data from controller to the view =- JSP/
			CustomerSavingVO customerSavingVO = optional.get();
			customerVO.setEmail(customerSavingVO.getEmail());
			customerVO.setName(customerSavingVO.getName());
			customerVO.setMobile(customerSavingVO.getMobile());
			customerVO.setAddress(customerSavingVO.getLocation());
			customerVO.setToken(cuid);
			logger.debug(customerSavingVO.toString());
			// model - is hash map which is used to carry data from controller to thyme
			// leaf!!!!!
			// model is similar to request scope in jsp and servlet
			model.addAttribute("customerVO", customerVO);
			return "customer/customerRegistration"; // thyme leaf
		}
	}

	@PostMapping("/customer/account/registration")
	public String createCustomer(@ModelAttribute CustomerVO customerVO, Model model) {
		// saving customer into database
		logger.debug(customerVO.toString());
		customerVO = customerService.createAccount(customerVO);
		// Write code to send email

		EmailVO mail = new EmailVO(customerVO.getEmail(), "javahunk2020@gmail.com",
				"Regarding Customer " + customerVO.getName() + "  userid and password", "", customerVO.getName());
		mail.setUsername(customerVO.getUserid());
		mail.setPassword(customerVO.getPassword());
		emailService.sendUsernamePasswordEmail(mail);
		System.out.println(customerVO);
		model.addAttribute("loginVO", new LoginVO());
		model.addAttribute("message", "Your account has been setup successfully , please check your email.");
		return "customer/login";
	}

	@GetMapping(value = { "/customer/account/enquiry", "/", "/mocha", "/welcome" })
	public String showCustomerEnquiryPage(Model model) {
		CustomerSavingVO customerSavingVO = new CustomerSavingVO();
		// model is map which is used to carry object from controller to view
		model.addAttribute("customerSavingVO", customerSavingVO);
		return "customer/customerEnquiry"; // customerEnquiry.html
	}

	@PostMapping("/customer/account/enquiry")
	public String submitEnquiryData(@ModelAttribute CustomerSavingVO customerSavingVO, Model model) {
		boolean status = customerEnquiryService.emailNotExist(customerSavingVO.getEmail());
		logger.info("Executing submitEnquiryData");
		if (status) {
			CustomerSavingVO response = customerEnquiryService.save(customerSavingVO);
			logger.debug("Hey Customer , your enquiry form has been submitted successfully!!! and appref "
					+ response.getAppref());
			model.addAttribute("message",
					"Hey Customer , your enquiry form has been submitted successfully!!! and appref "
							+ response.getAppref());
		} else {
			model.addAttribute("message", "Sorry , this email is already in use " + customerSavingVO.getEmail());
		}
		return "customer/success"; // customerEnquiry.html

	}
	
	@GetMapping("/customer/myProfile")
	public String myProfile(Model model, HttpSession session) {
		LoginVO  loginVO = (LoginVO) session.getAttribute("userSessionVO");
		if(loginVO !=null ) {
			String loginid=loginVO.getUsername();
			//get customer data 
			CustomerVO customer = customerService.getCustomerData(loginid);
			// add to model 
			model.addAttribute("customerVO", customer);
			// return to jsp 
			return "customer/myProfile";
		}else {
			return "customer/login";
		}
	}
	
	@PostMapping("/customer/updateProfile")
	public String updateProfile(@ModelAttribute CustomerVO customer, Model model, HttpSession session) {
		LoginVO  loginVO = (LoginVO) session.getAttribute("userSessionVO");
		if(loginVO !=null ) {
			//call customerService -> create new method for updateProfile -> call repository.save() 
			String message = customerService.updateProfile(customer);
			model.addAttribute("message", message);
			return "customer/myProfile";
		}else {
			return "customer/login";
		}
	}
	
	

	@GetMapping("/customer/createAccount") 
	public String createAccount(Model model, HttpSession session) {
		LoginVO  loginVO = (LoginVO) session.getAttribute("userSessionVO");
		if(loginVO !=null ) {
			String loginid=loginVO.getUsername();
			CustomerAccountInfoVO  accountVo = accountService.createAccount(loginid);
			model.addAttribute("accountVo", accountVo);
			// return to jsp 
			return "customer/accountCreate";
		}else {
			return "customer/login";
		}
	}
	@GetMapping("/customer/addPayee")
	public String addPayee(HttpSession session) {
		LoginVO  loginVO = (LoginVO) session.getAttribute("userSessionVO");
		if(loginVO !=null ) {
			return "customer/addPayee";
		}else {
			return "customer/login";
		}
	}
	
	@PostMapping("/customer/addPayee")
	public String addNewPayee(@ModelAttribute PayeeInfoVO payeeVO , HttpSession session, Model model) {
		LoginVO  loginVO = (LoginVO) session.getAttribute("userSessionVO");
		if(loginVO !=null ) {
			//call service
			payeeVO.setCustomerId(loginVO.getUsername());
			String message = payeeService.addPayee(payeeVO);
			model.addAttribute("message", message);
			return "customer/addPayee";
		}else {
			return "customer/login";
		}
	}
	
	@GetMapping("/customer/fundTransfer")
	public String fundTransfer( HttpSession session, Model model) {
		LoginVO  loginVO = (LoginVO) session.getAttribute("userSessionVO");
		if(loginVO !=null ) {
			List<PayeeInfoVO> payees = payeeService.findAllPayee(loginVO.getUsername());
			model.addAttribute("payeeList", payees);
			
			return "customer/fundTransfer";
		}else {
			return "customer/login";
		}
		
	}
	
	@PostMapping("/customer/transferFund")
	public String transferFund( HttpSession session, Model model, @ModelAttribute TransactionVO transaction) {
		LoginVO  loginVO = (LoginVO) session.getAttribute("userSessionVO");
	
		if(loginVO !=null ) {
			transaction.setCustomerId(loginVO.getUsername());
			transaction.setName(loginVO.getName());
			
			String message = transactionService.fundTransfer(transaction);
			model.addAttribute("message", message);
			
			return "customer/addPayee";
		}else {
			return "customer/login";
		}
		
	}
	
	@GetMapping("customer/showdatas")
	public String showDatas(HttpSession session, Model model) {
		
		LoginVO  loginVO = (LoginVO) session.getAttribute("userSessionVO");
		
		if(loginVO !=null ) {
			List<TransactionVO> transferData =  transactionService.showData();
			model.addAttribute("transferData", transferData);
			
		}
		return "customer/showtransferdata"; //.html
		
	}
	
	
	

}
