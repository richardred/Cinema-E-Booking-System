import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
 
public class Mail {
   
    /**
     * 1. Download JavaMail API
     * 2. Add the mail.jar , and all the jars in "lib" to project
     * 4. import java.util.Properties
          import javax.mail.*;
     */
 
    public void send_email(String email, int emailType) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host","smtp.gmail.com");   //name of host/mail server here, format: smtp.gmail.com
        props.put("mail.smtp.auth","true");             //attempts to authenticate user using the AUTH command
        props.put("mail.smtp.port","587" );             //mail server port # to specify protocol; 587 for TLS, 465 for SSL)
        props.put("mail.smtp.starttls.enable","true"); //enable TLS
 
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("sunnybot94","group9A4050");  //email without domain name, followed by password
            }
        });
 
        try {  
            String subject = subjectType(emailType);
            String body = bodyType(emailType);
           
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sunnybot94@gmail.com"));                                       //sender email with domain name
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));   //recipient email with domain name
            message.setSubject(subject);                                                                            //subject
            message.setText(body);                                                                   //body
            Transport.send(message, "sunnybot94@gmail.com", "group9A4050");                                     //sender email with domain name, password
 
            System.out.println("message sent");
 
        } catch(Exception e) {
            //if unsuccessful email transmission, explain error
            e.printStackTrace();
        }
    }
 
    /*  bodyType() description:
     *  returns a body statement depending on the emailType below:
     *  emailType==1 , verification of account registration
     *  emailType==2 , login of user notification
     *  emailType==3 , email promotion notification
     */
 
    private String bodyType(int emailType) {
        if (emailType==1)
            return "default email body of user account verification.";
        if (emailType==2)
            return "default email body of user login notification.";
        if (emailType==3)
            return "default email body of movie promotion.";
        return null;
    }
 
     /* subjectType() description:
     *  returns a subject statement depending on the emailType below:
     *  emailType==1 , verification of account registration
     *  emailType==2 , login of user notification
     *  emailType==3 , email promotion notification
     */
 
    private String subjectType(int emailType) {
        if (emailType==1)
            return "E-Cinema Booking: Verification of User Account Registration";
        if (emailType==2)
            return "E-Cinema Booking: Notification of User Login";
        if (emailType==3)
            return "E-Cinema Booking: New Online Promotion";
        return null;
    }
}