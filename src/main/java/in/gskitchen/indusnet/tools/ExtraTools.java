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

    public void sendOtpToEmail(String emailId, String otp) throws AddressException, MessagingException, IOException{
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        String emailBody = "<h2>Welcome to Indusnet Technology</h2>"
                + "<p>Recently You've sign up for our service. Please verify your account with this OTP. Your OTP is: </p>"
                + "<h4>" + otp + "</h4>"
                + "<small>If You did not sign up using this email, please ignore this message</small>";

        Session session = Session.getInstance(props, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("sabbira.shaikh@indusnet.co.in", "**********");
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("sabbira.shaikh@indusnet.co.in", false));

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailId));
        message.setSubject("[Indusnet] Verify your email");
        message.setContent(emailBody, "text/html");

        Transport.send(message);
    }
}
