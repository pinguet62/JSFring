package fr.pinguet62.jsfring.mock;

import org.springframework.context.annotation.Primary;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import lombok.Setter;

/**
 * Mock of {@link MailSender} for unit-test.
 * <p>
 * By default no {@link MailException} is thrown.<br>
 * If needed, throws {@link MailException} on demand, by calling {@link #mustThrow(boolean)}.
 *
 * @see MailSender
 */
@Component
@Primary // Mock
public class MailSenderThrowableMock implements MailSender {

    /** Define if the next sending must fail. */
    @Setter
    private boolean mustThrow = false;

    /** @see #send(SimpleMailMessage...) */
    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        send(new SimpleMailMessage[] { simpleMessage });
    }

    /**
     * @throws MailException
     *             If asked by {@link #mustThrow(boolean)}.
     */
    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        if (mustThrow)
            throw new MailSendException("As asked");
    }

}