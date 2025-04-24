package com.egov.template.common.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;



/**
 * packageName         : kr.or.krc.sicis.cmmn.util
 * fileName            : SendMailUtil
 * author              : rokku
 * date                : 2024-10-07
 * description         :
 * ===========================================================
 * DATE               AUTHOR              NOTE
 * 2024-10-07            rokku             최초 생성
 */
@Slf4j
@Component
@RequiredArgsConstructor
public final class SendMailUtil {

    private final JavaMailSender javaMailSender;

    private final SpringTemplateEngine templateEngine;


    public void sendDefaultEmail(String to, String subject, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }

    public void sendEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);  // true는 HTML로 전송을 의미

        javaMailSender.send(message);
    }

    public String generateEmailContent(String templateName, Map<String, Object> model) {
        Context context = new Context();
        context.setVariables(model);
        return templateEngine.process(templateName, context);
    }

}
