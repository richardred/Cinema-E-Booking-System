import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Mail {
	public Mail() {
		
	}
	/**
	 * 1. Download JavaMail API
	 * 2. Add the mail.jar , and all the jars in "lib" to project
	 * 4. import java.util.Properties
	      import javax.mail.*; 
	 */	
	
	public void send_email(String email, String subject, String body) throws MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.host","smtp.gmail.com"); 								//name of host/mail server here, format: smtp.gmail.com
		props.put("mail.smtp.auth", "true");										//attempts to authenticate user using the AUTH command
		props.put("mail.smtp.port","587");											//mail server port # to specify protocol; 587 for TLS, 465 for SSL)
		props.put("mail.smtp.starttls.enable", "true");								//enable TLS


		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication("sunnybot94","group9A4050");	//email without domain name, followed by password
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("sunnybot94@gmail.com"));										//sender email with domain name
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));   //recipient email with domain name
			message.setSubject(subject);																			//subject
			message.setText(body);                                                                   //body
			Transport.send(message, "sunnybot94@gmail.com", "group9A4050");  									//sender email with domain name, password

			System.out.println("message sent");

		} catch(Exception e) {
			//if unsuccessful email transmission, explain error
			e.printStackTrace();
		}
	}
}

