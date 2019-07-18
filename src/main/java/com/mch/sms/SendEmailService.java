package com.mch.sms;

/**
 * 邮件服务
 *
 * @author niguang
 *
 * @Date 2018年8月27日
 */
public interface SendEmailService {
	
	/**
	 * 发送邮件（支持html格式）
	 * @param subject 邮件主题
	 * @param content 邮件正文
	 * @param to 收件人邮箱，支持多个收件人
	 */
	public void sendSimpleMail(String subject, String content, String... to);

	/**
	 * 带附件发送
	 * @param subject 邮件主题
	 * @param content 邮件正文
	 * @param filePath 附件路径
	 * @param to 多个收件人
	 */
	public void sendAttachmentsMail(String subject, String content, String filePath, String... to);

}
