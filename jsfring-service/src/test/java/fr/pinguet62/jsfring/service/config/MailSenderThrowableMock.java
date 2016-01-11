package fr.pinguet62.jsfring.service.config;

import org.springframework.context.annotation.Primary;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

/**
 * Mock of {@link MailSender} for unit-test.
 * <p>
 * By default no {@link MailException} is thrown.<br>
 * If needed, this {@link MailException} on demand, by calling
 * {@link #mustThrow()}.
 *
 * @see MailSender
 */
@Component
@Primary // Mock
public class MailSenderThrowableMock implements MailSender {

    /** Define if the next sending must fail. */
    private boolean mustThrow = false;

    /**
     * The next send will fail.
     *
     * @param mustThrow If next call must fail.
     */
    public void mustThrow(boolean mustThrow) {
        this.mustThrow = mustThrow;
    }

    /** @see #send(SimpleMailMessage...) */
    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        send(new SimpleMailMessage[] { simpleMessage });
    }

    /** @throws MailException If asked by {@link #mustThrow()}. */
    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        if (mustThrow)
            throw new MailSendException("As asked");
    }

}