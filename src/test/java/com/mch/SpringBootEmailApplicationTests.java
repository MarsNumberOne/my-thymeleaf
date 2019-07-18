package com.mch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootEmailApplicationTests {

    @Value("${spring.mail.username}")
    private String fromEmailAddr;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * @throws MessagingException 
     * @Title: sendHtmlEmail
     * @Description: 发送thymeleaf模板邮件
     * @return void
     * @throws
     */
    @Test
    public void sendHtmlEmail() throws MessagingException, UnsupportedEncodingException {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        //开启带附件true
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true);
        Context context = new Context();
        context.setVariable("name","Java邮件测试-阳仁");
        //获取模板html代码
        String process = templateEngine.process("index", context);
        try {
            //发件人，昵称  messageHelper.setFrom("machunhuimail@foxmail.com");
            messageHelper.setFrom(new InternetAddress("machunhuimail@foxmail.com","mch"));
            //收件人，可以接受多个String[]
            messageHelper.setTo("3357779533@qq.com");
            //邮件主题
            messageHelper.setSubject("主题");
            //邮件内容
            messageHelper.setText(process, true);
            //发送带静态资源的邮件 图片
//            FileSystemResource avatar = new FileSystemResource(
//                    new File("F:\\workspace\\SpringBootEmail\\src\\main\\resources\\templates\\img\\bappy.jpg"));
//            //<img src="cid:avatar" />
//            messageHelper.addInline("avatar", avatar);
              //发送带附件的邮件
//            helper.addAttachment("图片.jpg", file);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mailMessage);
    }
}