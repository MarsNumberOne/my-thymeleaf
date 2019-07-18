package com.mch.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;

@Service
public class SendEmailServiceImpl implements SendEmailService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username:no_reply@gshopper.com}")
	private String from;
	
	@Value("${email.from.name:Gshopper}")
	private String nickname;
	
	
	@Override
	@Async("asyncServiceExecutor")
	public void sendSimpleMail( String subject, String content,String... to) {
		MimeMessage message = mimeMessageBuilder(subject,content,to);
		mailSender.send(message);
	}
	
	@Override
	@Async("asyncServiceExecutor")
	public void sendAttachmentsMail ( String subject, String content,String filePath,String... to) {
		MimeMessage message = attachmentsMessageBuilder(subject,content,filePath,to);
		mailSender.send(message);
	}
	
	private MimeMessage attachmentsMessageBuilder(String subject, String content, String filePath, String... to) {
		MimeMessage message = mailSender.createMimeMessage();
        try {
        MimeMessageHelper helper = mimeMessageHelperBuilder(message,subject,content,to);
        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        helper.addAttachment(fileName, file);
		} catch (Exception e) {
			logger.error("发送邮件时发生异常！", e);
		}
        return message;
        
	}
	
	private MimeMessage mimeMessageBuilder(String subject, String content, String... to) {
		MimeMessage message = mailSender.createMimeMessage();
        mimeMessageHelperBuilder(message,subject,content,to);
        return message;
	}
	
	private MimeMessageHelper mimeMessageHelperBuilder(MimeMessage message, String subject, String content, String... to) {
        try {
        	MimeMessageHelper helper = new MimeMessageHelper(message, true);
        	helper.setFrom(new InternetAddress(from,nickname));
        	helper.setTo(to);
        	helper.setSubject(subject);
        	helper.setText(content,true);
        	return helper;
        } catch (MessagingException | UnsupportedEncodingException e) {
            logger.error("发送邮件时发生异常！", e);
        }
        return null;
	}

}
