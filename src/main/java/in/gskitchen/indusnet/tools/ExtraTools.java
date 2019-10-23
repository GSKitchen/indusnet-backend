package in.gskitchen.indusnet.tools;

import in.gskitchen.indusnet.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

/**
 * This class contain some method to use these  as tool.
 */
public class ExtraTools {

    @Autowired
    private OtpRepository otpRepository;


    /*
    public void generateOtp(User user){
        Random random = new Random();
        Otp otp = new Otp();
        long otpNumber = random.nextInt(999999) + 111111;
        otp.setUser(user);
        otp.setOtp(String.valueOf(otpNumber));
        otpRepository.save(otp);
        sendOtpToEmail("", "");
    } */

    /**
     * This method is used to GEnerate OTP each it's called.
     * @return String
     */
    public static String generateOtp(){
        Random random = new Random();
        long otpNumber = random.nextInt(999999) + 111111;
        return String.valueOf(otpNumber);
    }

    /*
    public void sendOtpToEmail(String emailId, String otp){
        try {
            this.sendMail();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * This method send email to User.
     * @param emailId as String
     * @param otp as String
     * @throws AddressException
     * @throws MessagingException
     * @throws IOException
     */
    public void sendOtpToEmail(String emailId, String otp) throws AddressException, MessagingException, IOException{
        //setting up email server configuration
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        //Email format body
        String emailBody = "<h2>Welcome to Indusnet Technology</h2>"
                + "<p>Recently You've sign up for our service. Please verify your account with this OTP. Your OTP is: </p>"
                + "<h4>" + otp + "</h4>"
                + "<small>If You did not sign up using this email, please ignore this message</small>";

        //Authorise and Session creation
        Session session = Session.getInstance(props, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("sabbira.shaikh@indusnet.co.in", "myINTpwd$");
            }
        });

        //construct email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("sabbira.shaikh@indusnet.co.in", false));

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailId));
        message.setSubject("[Indusnet] Verify your email");
        message.setContent(emailBody, "text/html");

        //sending email
        Transport.send(message);
    }
}
