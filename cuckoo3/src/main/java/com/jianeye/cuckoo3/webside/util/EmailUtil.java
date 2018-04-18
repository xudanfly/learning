package com.jianeye.cuckoo3.webside.util;


import javax.mail.AuthenticationFailedException;
import jodd.mail.Email;
import jodd.mail.MailException;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * EmailUtil.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 邮件发送工具类
 */
@Component
public class EmailUtil {	

	@Value("${mail.username}")
	private String mailUserName;

	@Value("${mail.password}")
	private String mailPassword;
	
	@Value("${mail.smtpServer}")
	private String smtpServerUrl;


	/**
	 * 发送126邮箱
	 * 
	 * @param toMail
	 * @param subject
	 * @param text
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean send126Mail(String toMail, String subject, String text) throws AuthenticationFailedException,MailException{
		Email email = Email.create()
							.from(mailUserName)
							.to(toMail)
							.subject(subject)
							.addText(text);
		SmtpServer smtpServer = SmtpServer.create(smtpServerUrl)
				.authenticateWith(mailUserName, mailPassword);
		SendMailSession session = smtpServer.createSession();
		session.open();
		session.sendMail(email);
		session.close();
		return true;
	}
}
