package fr.pinguet62.jsfring.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class MailConfig {

    // TODO Check if it's necessary
    // properties.put("mail.smtp.auth", true);
    // properties.put("mail.smtp.starttls.enable", true);
    // properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

    @Bean
    public SimpleMailMessage forgottenPasswordMessage() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("sender@gmail.com");
        mailMessage.setSubject("[JSFring] Reset password");
        mailMessage
                .setText("<![CDATA[\n                Hello\n                \n                Login: %s\n                New password: %s\n                ]]>");

        return mailMessage;
    }

}