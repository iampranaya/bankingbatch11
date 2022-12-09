package com.rab3tech.email.service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.rab3tech.customer.dao.repository.CustomerRepository;
import com.rab3tech.customer.service.CustomerStatementService;
import com.rab3tech.dao.entity.Customer;
import com.rab3tech.vo.EmailVO;
import com.rab3tech.vo.PayeeInfoVO;
import com.rab3tech.vo.StatementVO;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
    private SpringTemplateEngine templateEngine;
	
	@Autowired
	private CustomerStatementService customerStatementService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	@Override
	@Async("threadPool")
	public String sendRegistrationEmail(EmailVO mail)  {
		
		 try {
		    MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message,
	                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
	                StandardCharsets.UTF_8.name());
	        Context context = new Context();
	        Map<String,Object> props=new HashMap<>();
	        props.put("name", mail.getName());
	        props.put("sign", "By Cubic Bank");
	        props.put("location", "Fremont CA100 , USA");
	        props.put("email", "javahunk2020@gmail.com");
	        props.put("registrationlink", mail.getRegistrationlink());
	        context.setVariables(props);
	        String html = templateEngine.process("send-registration-link", context);
	        helper.setTo(mail.getTo());
	        helper.setText(html, true);
	        helper.setSubject(mail.getSubject());
	        helper.setFrom(mail.getFrom());
	        
	        File cfile=new ClassPathResource("images/registration-banner.png", EmailServiceImpl.class.getClassLoader()).getFile();
	        byte[] cbytes=Files.readAllBytes(cfile.toPath());
	        InputStreamSource cimageSource =new ByteArrayResource(cbytes);
	        helper.addInline("cb", cimageSource, "image/png");
	        
	        
	        File file=new ClassPathResource("images/bank-icon.png", EmailServiceImpl.class.getClassLoader()).getFile();
	        byte[] bytes=Files.readAllBytes(file.toPath());
	        InputStreamSource imageSource =new ByteArrayResource(bytes);
	        helper.addInline("bankIcon", imageSource, "image/png");
	        
	        javaMailSender.send(message);
		 }catch (Exception e) {
			e.printStackTrace();
		 }   
	        return "done";
	}
	
	
	@Override
	@Async("threadPool")
	public String sendUsernamePasswordEmail(EmailVO mail)  {
		
		 try {
		    MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message,
	                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
	                StandardCharsets.UTF_8.name());
	        Context context = new Context();
	        Map<String,Object> props=new HashMap<>();
	        props.put("name", mail.getName());
	        props.put("username", mail.getUsername());
	        props.put("password", mail.getPassword());
	        props.put("sign", "Banking Application");
	        props.put("location", "Fremont CA100 , USA");
	        props.put("email", "javahunk2020@gmail.com");
	        context.setVariables(props);
	        String html = templateEngine.process("password-email-template", context);
	        helper.setTo(mail.getTo());
	        helper.setText(html, true);
	        helper.setSubject("Regarding your banking username and password.");
	        helper.setFrom(mail.getFrom());
	        File cfile=new ClassPathResource("images/password.jpg", EmailServiceImpl.class.getClassLoader()).getFile();
	        byte[] cbytes=Files.readAllBytes(cfile.toPath());
	        InputStreamSource cimageSource =new ByteArrayResource(cbytes);
	        helper.addInline("cb", cimageSource, "image/png");
	        
	        File file=new ClassPathResource("images/bank-icon.png", EmailServiceImpl.class.getClassLoader()).getFile();
	        byte[] bytes=Files.readAllBytes(file.toPath());
	        InputStreamSource imageSource =new ByteArrayResource(bytes);
	        helper.addInline("bankIcon", imageSource, "image/png");
	        
	        javaMailSender.send(message);
		 }catch (Exception e) {
			e.printStackTrace();
		 }   
	        return "done";
	}

	
	@Override
	@Async("threadPool")
	public String sendEquiryEmail(EmailVO mail)  {
		
		 try {
		    MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message,
	                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
	                StandardCharsets.UTF_8.name());
	        Context context = new Context();
	        Map<String,Object> props=new HashMap<>();
	        props.put("name", mail.getName());
	        props.put("sign", "Banking Application");
	        props.put("location", "Fremont CA100 , USA");
	        props.put("email", "javahunk2020@gmail.com");
	        context.setVariables(props);
	        String html = templateEngine.process("enquiry-email-template", context);
	        helper.setTo(mail.getTo());
	        helper.setText(html, true);
	        helper.setSubject("Regarding Account enquiry to open an account.");
	        helper.setFrom(mail.getFrom());
	        
	        
	        File cfile=new ClassPathResource("images/cb1.png", EmailServiceImpl.class.getClassLoader()).getFile();
	        byte[] cbytes=Files.readAllBytes(cfile.toPath());
	        InputStreamSource cimageSource =new ByteArrayResource(cbytes);
	        helper.addInline("cb", cimageSource, "image/png");
	        
	        
	        File file=new ClassPathResource("images/bank-icon.png", EmailServiceImpl.class.getClassLoader()).getFile();
	        byte[] bytes=Files.readAllBytes(file.toPath());
	        InputStreamSource imageSource =new ByteArrayResource(bytes);
	        helper.addInline("bankIcon", imageSource, "image/png");
	        
	        javaMailSender.send(message);
		 }catch (Exception e) {
			e.printStackTrace();
		 }   
	        return "done";
	}


	@Override
	@Async("threadPool") //asynchronous call
	public void addPayeeEmail(EmailVO mail, PayeeInfoVO vo) {
		 try {
			    MimeMessage message = javaMailSender.createMimeMessage();
		        MimeMessageHelper helper = new MimeMessageHelper(message,
		                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
		                StandardCharsets.UTF_8.name());
		        Context context = new Context();
		        Map<String,Object> props=new HashMap<>();
		        props.put("name", mail.getName());
		        props.put("sign", "Banking Application");
		        props.put("location", "Fremont CA100 , USA");
		        props.put("email", "javahunk2020@gmail.com");
		        props.put("accountNo", vo.getPayeeAccountNo());
		        props.put("payeeName", vo.getPayeeName());
		        props.put("remarks", vo.getRemarks());
		        context.setVariables(props);
		        String html = templateEngine.process("addPayee-email-template", context);
		        helper.setTo(mail.getTo());
		        helper.setText(html, true);
		        helper.setSubject(mail.getSubject());
		        helper.setFrom(mail.getFrom());
		        
		        
		        File cfile=new ClassPathResource("images/cb1.png", EmailServiceImpl.class.getClassLoader()).getFile();
		        byte[] cbytes=Files.readAllBytes(cfile.toPath());
		        InputStreamSource cimageSource =new ByteArrayResource(cbytes);
		        helper.addInline("cb", cimageSource, "image/png");
		        
		        
		        File file=new ClassPathResource("images/bank-icon.png", EmailServiceImpl.class.getClassLoader()).getFile();
		        byte[] bytes=Files.readAllBytes(file.toPath());
		        InputStreamSource imageSource =new ByteArrayResource(bytes);
		        helper.addInline("bankIcon", imageSource, "image/png");
		        
		        javaMailSender.send(message);
			 }catch (Exception e) {
				e.printStackTrace();
			 }   
		       
	}


	@Override
	public void sendAccountStatement(EmailVO mail) {
		try {
			 Optional<Customer> customerInfo = customerRepository.findByEmail(mail.getUsername());
			
			 MimeMessage message = javaMailSender.createMimeMessage();
		        MimeMessageHelper helper = new MimeMessageHelper(message,
		                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
		                StandardCharsets.UTF_8.name());
		        Context context = new Context();
		        Map<String,Object> props=new HashMap<>();
		        List<StatementVO> statementData = customerStatementService.showCustomerStatement(mail.getUsername());
		       		        
		        props.put("statementData", statementData);
		        props.put("fromAccount", mail.getUsername());
		        props.put("name", customerInfo.get().getName());
		        props.put("sign", "Banking Application");
		        props.put("location", "Fremont CA100 , USA");
		        props.put("email", "javahunk2020@gmail.com");
		        context.setVariables(props);
		        
		        String html = templateEngine.process("sendStatement-email-template", context);
		        helper.setTo(mail.getTo());
		        helper.setText(html, true);
		        helper.setSubject("Your account statement details.");
		        helper.setFrom(mail.getFrom());
		        //helper.setTo("arti36011@gmail.com");
		        
		        File cfile=new ClassPathResource("images/cb1.png", EmailServiceImpl.class.getClassLoader()).getFile();
		        byte[] cbytes=Files.readAllBytes(cfile.toPath());
		        InputStreamSource cimageSource =new ByteArrayResource(cbytes);
		        helper.addInline("cb", cimageSource, "image/png");
		        
		        
		        File file=new ClassPathResource("images/bank-icon.png", EmailServiceImpl.class.getClassLoader()).getFile();
		        byte[] bytes=Files.readAllBytes(file.toPath());
		        InputStreamSource imageSource =new ByteArrayResource(bytes);
		        helper.addInline("bankIcon", imageSource, "image/png");
		        
		        javaMailSender.send(message);    
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
		
	}


