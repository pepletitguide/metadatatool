package com.letitguide.metadatatool;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SimpleMail {

    private String mailHostName;
    private String mailUserName;
    private String mailPassword;
    private String from;
    private String to;
    private String subject;
    private String body;

    public SimpleMail(String hostName, String userName, String password, String from,
            String to, String subject, String body) {
        this.mailHostName = hostName;
        this.mailUserName = userName;
        this.mailPassword = password;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }
    
    public SimpleMail(String from, String to, String subject, String body) {
        this.mailHostName = Constants.MAIL_HOSTNAME;
        this.mailUserName = Constants.MAIL_USERNAME;
        this.mailPassword = Constants.MAIL_PASSWORD;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public synchronized void send() throws MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.host", mailHostName);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", Constants.MAIL_SMTP_PORT);

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mailUserName, mailPassword);
                    }
                });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        if (to.indexOf(',') > 0) {
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        } else {
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        }
        String encodingOptions = "text/html; charset=UTF-8";
        message.setHeader("Content-Type", encodingOptions);
        message.setSubject(subject, "UTF-8");
        message.setContent(body, encodingOptions);
        Transport.send(message);
    }
}