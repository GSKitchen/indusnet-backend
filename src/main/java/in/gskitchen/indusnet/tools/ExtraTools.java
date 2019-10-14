package in.gskitchen.indusnet.tools;

import in.gskitchen.indusnet.exceptions.UserNotFoundException;
import in.gskitchen.indusnet.model.Otp;
import in.gskitchen.indusnet.model.User;
import in.gskitchen.indusnet.repository.OtpRepository;
import in.gskitchen.indusnet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
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
        //
    }
}
