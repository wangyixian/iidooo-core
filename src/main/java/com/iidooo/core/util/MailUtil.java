package com.iidooo.core.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class MailUtil {
    private static final Logger logger = Logger.getLogger(MailUtil.class);

    public static boolean sendMail(Properties mailProperties, String content, String to) {
        try {

            String smtp = mailProperties.getProperty("SEND_MAIL_SMTP");
            final String fromAddress = mailProperties.getProperty("SEND_MAIL_FROM_ADDRESS");
            final String fromPassword = mailProperties.getProperty("SEND_MAIL_FROM_PASSWORD");
            String fromName = mailProperties.getProperty("SEND_MAIL_FROM_NAME");
            String subject = mailProperties.getProperty("SEND_MAIL_SUBJECT");

            Properties props = new Properties();
            props.put("mail.smtp.host", smtp);
            props.put("mail.host", smtp);
            props.setProperty("mail.smtp.auth", "true");

            // 根据邮件会话属性和密码验证器构造一个发送邮件的session
            Session mailSession = Session.getDefaultInstance(props, new Authenticator() {
                // mail验证
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromAddress, fromPassword);
                }
            });

            // 根据session创建一个邮件消息
            MimeMessage mimeMessage = new MimeMessage(mailSession);
            // 发件人邮箱地址设置
            Address from = new InternetAddress(fromAddress, fromName, "UTF-8");
            mimeMessage.setFrom(from);
            // 主题设置
            mimeMessage.setSubject(subject, "UTF-8");
            // 设置邮件消息发送的时间
            mimeMessage.setSentDate(new Date());
            // 收件人邮箱地址设置
            InternetAddress[] toList = InternetAddress.parse(to);
            mimeMessage.addRecipients(Message.RecipientType.TO, toList);
            // 抄送收件人邮箱地址设置
            // if (cc != null) {
            // InternetAddress[] ccAdr = null;
            // ccAdr = InternetAddress.parse(cc);
            // mimeMessage.addRecipients(Message.RecipientType.CC, ccAdr);
            // }

            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart multipart = new MimeMultipart();
            MimeBodyPart mbpText = new MimeBodyPart();
            mbpText.setText(content, "UTF-8");
            multipart.addBodyPart(mbpText);
            // 邮件内容设定
            // if (filePath != null) {
            // for (int j = 0; j < filePath.size(); j++) {
            // MimeBodyPart mbpFile = new MimeBodyPart();
            // FileDataSource fds = new FileDataSource(filePath.get(j));
            // mbpFile.setDataHandler(new DataHandler(fds));
            // mbpFile.setFileName(MimeUtility.encodeText(fds.getName()));
            // mp.addBodyPart(mbpFile);
            // }
            // }
            mimeMessage.setContent(multipart);
            Transport.send(mimeMessage);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return false;
        }
    }

    public static void main(String[] args) {
        
    }
}
