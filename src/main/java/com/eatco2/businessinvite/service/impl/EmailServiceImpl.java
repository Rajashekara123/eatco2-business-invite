/**
 * 
 */
package com.eatco2.businessinvite.service.impl;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.eatco2.businessinvite.dto.BusinessInviteDTO;
import com.eatco2.businessinvite.exception.BusinessInviteException;
import com.eatco2.businessinvite.service.EmailService;

/**
 * @author Rajashekara
 *
 */
@Service
public class EmailServiceImpl implements EmailService {

	Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	@Autowired
	private Environment env;
	@Value("${spring.mail.username}")
	private String userName;

	@Value("${spring.mail.from}")
	private String fromEmail;

	@Value("${spring.mail.host}")
	private String host;

	@Value("${spring.mail.port}")
	private int port;

	@Value("${spring.mail.password}")
	private String password;

	@Value("${spring.mail.properties.mail.smtp.auth}")
	private String auth;

	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private String ttls;

	@Value("${businessinvite.url}")
	private String businessinviteURL;

	@Override
	public String processAndSendEmail(BusinessInviteDTO businessInviteDTO, boolean reminderEmail)
			throws BusinessInviteException {
		return sendMail(getMessageBody(businessInviteDTO), businessInviteDTO.getInviteeEmailAddress(),
				env.getProperty("BUSINSESSINVITE_EMAIL_SUBJECT"), reminderEmail);

	}

	/**
	 * @param businessInviteDTO
	 * @return
	 */
	private String getMessageBody(BusinessInviteDTO businessInviteDTO) {
		StringBuilder msg = new StringBuilder();
		StringBuilder msgPart = new StringBuilder();
		msgPart.append(businessInviteDTO.getInviterBusinessName());
		msgPart.append(" has referred you... ");
		msgPart.append(
				"<a href=\"" + businessinviteURL + "\" style=\"text-decoration: none;color:#0E855F;\">register now \n");
		msg.append(
				"<body style=\"padding: 0; margin: 0; -webkit-text-size-adjust:100%; -ms-text-size-adjust:100%; width:100% !important; background-color:#F2F2F2;\" bgcolor=\"#F2F2F2\" marginheight=\"0\" marginwidth=\"0\" topmargin=\"0\" rightmargin=\"0\" bottommargin=\"0\" leftmargin=\"0\">\n"
						+ "   <table id=\"BackgroundTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" height=\"100%\" bgcolor=\"#F2F2F2\" style=\"margin:0; padding:0; width:100% !important; line-height: 100% !important; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; background-color:#F2F2F2;\">\n"
						+ "   <tbody>\n" + "      <tr>\n"
						+ "         <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse;\">\n"
						+ "            <table id=\"ColumnTable\" class=\"mobilefullwidth\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#F2F2F2\" width=\"600\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; background-color:#F2F2F2;\">\n"
						+ "   <tbody>\n" + "      <tr>\n" + "         <td style=\"border-collapse: collapse;\">\n"
						+ "            <table id=\"PreHeaderTable\" class=\"mobiledisplayno\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#F2F2F2\" width=\"100%\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; background-color:#F2F2F2; width:100%!important;\">\n"
						+ "               <tbody>\n" + "                  <tr>\n"
						+ "                     <td height=\"30\" style=\"border-collapse: collapse;\" align=\"left\" valign=\"middle\">&nbsp;</td>\n"
						+ "                  </tr>\n" + "               </tbody>\n" + "            </table>\n"
						+ "            <!--PreHeaderTable-->\n"
						+ "            <table id=\"BarTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#4288a7\" width=\"100%\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; background-color:#4288a7; width:100%!important;\">\n"
						+ "               <tbody>\n" + "                  <tr>\n"
						+ "                     <td class=\"mobilemargin\" bgcolor=\"#4288a7\" width=\"30\" style=\"border-collapse: collapse;\"></td>\n"
						+ "                     <td align=\"left\" valign=\"middle\" style=\"border-collapse: collapse; text-align: left; background-color:#4288a7;min-width:210px !important;\">\n"
						+ "                        <a target=\"_blank\" href=\"https://eatco2.com\" style=\"font-family: Helvetica, Arial, sans-serif; font-size:24px; line-height:1.4; font-weight: normal; color: #FFFFFF; text-align: left; text-decoration: none;\">\n"
						+ "                        <img alt=\"komoot\" width=\"70\" height=\"60\" border=\"0\" style=\"outline:none; text-decoration:none; -ms-interpolation-mode: bicubic; display:block;\" src=\"https://s3.amazonaws.com/static.komoot.de/email/welcome-mail/logo.jpg\">\n"
						+ "                        </a>\n" + "                     </td>\n"
						+ "                  </tr>\n" + "               </tbody>\n" + "            </table>\n"
						+ "            <!--BarTable-->\n"
						+ "            <table id=\"HeaderTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#91CAE3\" width=\"100%\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; width:100%!important; background-color:#91CAE3;\">\n"
						+ "               <tbody>\n" + "                  <tr>\n"
						+ "                     <td align=\"center\" valign=\"middle\" style=\"border-collapse: collapse; background-color:#91CAE3;\">\n"
						+ "                        <a style=\"color:#FFFFFF; text-decoration:none;\" target=\"_blank\" href=\"http://eatco2.com\">\n"
						+ "                        <img class=\"mobilefullwidth\" alt=\"Schön dass du dabei bist!\" width=\"600\" height=\"270\" border=\"0\" style=\"outline:none; -ms-interpolation-mode: bicubic; display:block;\" src=\"https://s3.amazonaws.com/static.komoot.de/email/welcome-mail/header.jpg\">\n"
						+ "                        </a>\n" + "                     </td>\n"
						+ "                  </tr>\n" + "               </tbody>\n" + "            </table>\n"
						+ "            <!--HeaderTable-->\n"
						+ "            <table id=\"SectionOneTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#A3C556\" width=\"100%\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; background-color:#A3C556; width:100%!important;\">\n"
						+ "               <tbody>\n" + "                  <tr>\n"
						+ "                     <td class=\"mobilemargin\" bgcolor=\"#A3C556\" width=\"30\" style=\"border-collapse: collapse;\"></td>\n"
						+ "                     <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; font-family: Helvetica, Arial, sans-serif; font-size:28px; line-height:1.4; font-weight: normal; color: #ffffff; text-align: center; background-color:#A3C556;\">\n"
						+ "                        <span style=\"font-weight: bold;\"> you’re invited! " + "</span>,"
						+ msgPart + "                   </td>\n"
						+ "                     <td class=\"mobilemargin\" bgcolor=\"#A3C556\" width=\"30\" style=\"border-collapse: collapse;\"></td>\n"
						+ "                  </tr>\n" + "                  <tr>\n"
						+ "                     <td colspan=\"3\" bgcolor=\"#A3C556\" height=\"10\" style=\"border-collapse: collapse;\"></td>\n"
						+ "                  </tr>\n" + "                  <tr>\n"
						+ "                     <td class=\"mobilemargin\" bgcolor=\"#A3C556\" width=\"30\" style=\"border-collapse: collapse;\"></td>\n"
						+ "                     <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; font-family: Helvetica, Arial, sans-serif; font-size:18px; line-height:1.4; font-weight: normal; color: #e9f5cb; text-align: center; background-color:#A3C556;\">\n"
						+ "                        Europe’s #1 carbon compensation app welcomes you to the family. Keep reading to find out how to make EATCo2 your own.\n"
						+ "                     </td>\n"
						+ "                     <td class=\"mobilemargin\" bgcolor=\"#A3C556\" width=\"30\" style=\"border-collapse: collapse;\"></td>\n"
						+ "                  </tr>\n" + "                  <tr>\n"
						+ "                     <td colspan=\"3\" bgcolor=\"#A3C556\" height=\"40\" style=\"border-collapse: collapse;\"></td>\n"
						+ "                  </tr>\n" + "               </tbody>\n" + "            </table>\n"
						+ "            <!--SectionOneTable-->\n" + "</body>");
		return msg.toString();
	}

	private String sendMail(String msg, String toMailId, String subject, boolean reminderEmail)
			throws BusinessInviteException {
		JavaMailSenderImpl mailSender = null;
		try {
			mailSender = new JavaMailSenderImpl();
			mailSender.setHost(host);
			mailSender.setPort(port);
			mailSender.setUsername(userName);
			mailSender.setPassword(password);
			Properties properties = new Properties();
			properties.setProperty("mail.smtp.auth", auth);
			properties.setProperty("mail.smtp.starttls.enable", ttls);
			mailSender.setJavaMailProperties(properties);

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			helper.setTo(toMailId);
			helper.setSubject(subject);
			helper.setFrom(fromEmail);
			helper.setText(msg, true); // Use this or above line.
			mailSender.send(mimeMessage);
			logger.info("Email send for {} for Subject {}", toMailId, subject);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in sending mail for {}", toMailId);
			throw new BusinessInviteException(env.getProperty("ERROR_MESSAGE_EMAIL").trim());
		}
		if (reminderEmail) {
			return env.getProperty("REMINDER_MAIL_SUCCESSFUL_MESSAGE").trim();
		} else {
			return env.getProperty("MAIL_SUCCESSFUL_MESSAGE").trim();
		}
	}

}
