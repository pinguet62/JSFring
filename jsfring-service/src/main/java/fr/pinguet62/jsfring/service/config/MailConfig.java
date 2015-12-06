package fr.pinguet62.jsfring.service.config;

import java.util.Properties;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Inject
    private Environment environment;

    @Bean
    public SimpleMailMessage forgottenPasswordMessage() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("sender@gmail.com");
        mailMessage.setSubject("[JSFring] Reset password");
        mailMessage.setText(
                "<![CDATA[\n                Hello\n                \n                Login: %s\n                New password: %s\n                ]]>");

        return mailMessage;
    }

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(environment.getProperty("SMTP_HOST"));
        mailSender.setPort(Integer.parseInt(environment.getProperty("SMTP_PORT")));
        mailSender.setUsername(environment.getProperty("SMTP_USERNAME"));
        mailSender.setPassword(environment.getProperty("SMTP_PASSWORD"));

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mailSender.setJavaMailProperties(properties);

        return mailSender;
    }

}