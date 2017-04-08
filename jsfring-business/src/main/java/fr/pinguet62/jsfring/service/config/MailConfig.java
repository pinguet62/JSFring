package fr.pinguet62.jsfring.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class MailConfig {

    @Bean
    public SimpleMailMessage forgottenPasswordMessage() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("sender@gmail.com");
        mailMessage.setSubject("[JSFring] Reset password");
        mailMessage.setText("<![CDATA[\n" //
                + "                Hello\n" //
                + "                \n" //
                + "                Login: %s\n" //
                + "                New password: %s\n" //
                + "                ]]>");

        return mailMessage;
    }

}