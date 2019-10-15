package in.gskitchen.indusnet.tools;

import in.gskitchen.indusnet.exceptions.UserNotFoundException;
import in.gskitchen.indusnet.model.Otp;
import in.gskitchen.indusnet.model.User;
import in.gskitchen.indusnet.repository.OtpRepository;
import in.gskitchen.indusnet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

public class ExtraTools {

    @Autowired
    private OtpRepository otpRepository;


    public void generateOtp(User user){
        Random random = new Random();
        Otp otp = new Otp();
        long otpNumber = random.nextInt(999999) + 111111;
        otp.setUser(user);
        otp.setOtp(String.valueOf(otpNumber));
        otpRepository.save(otp);
        sendOtpToEmail("", "");
    }

    public static String generateOtp(){
        Random random = new Random();
        long otpNumber = random.nextInt(999999) + 111111;
        return String.valueOf(otpNumber);
    }

    public void sendOtpToEmail(String emailId, String otp){
        try {
            this.sendMail();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMail() throws AddressException, MessagingException, IOException{
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("sabbira.shaikh@indusnet.co.in", "myINTpwd$");
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("sabbira.shaikh@indusnet.co.in", false));

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("sabbir.sio@gmail.com"));
        message.setSubject("[Indusnet] Verify your email");
        message.setContent("Your OTP is ******. Have a nice day", "text/html");

        Transport.send(message);
    }
}
