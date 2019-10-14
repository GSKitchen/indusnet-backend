package in.gskitchen.indusnet;

import in.gskitchen.indusnet.exceptions.UserNotFoundException;
import in.gskitchen.indusnet.model.Otp;
import in.gskitchen.indusnet.model.User;
import in.gskitchen.indusnet.repository.OtpRepository;
import in.gskitchen.indusnet.repository.UserRepository;
import in.gskitchen.indusnet.tools.ExtraTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class UserResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpRepository otpRepository;

    private ExtraTools tools = new ExtraTools();

    @GetMapping("/users")
    public List<User> userList(){
        return userRepository.findAll();
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> retrieveUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) throw new UserNotFoundException("id - " + id);
        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody User user){
        User savedUser = userRepository.save(user);
        Otp otp = new Otp();
        otp.setOtp(ExtraTools.generateOtp());
        otp.setUser(savedUser);
        otpRepository.save(otp);
        //tools.generateOtp(savedUser);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/verify-email")
    public String verifyEmail(@RequestBody String userResponse){
        String[] userResp = userResponse.trim().split(",");
        User user = userRepository.findByEmail(userResp[1]);
        if(user.getId() < 0) throw new UserNotFoundException("emailId - " + userResp[1]);
        Otp otp = otpRepository.findByUser(user);
        if(otp.getOtp().equals(userResp[0])){
            user.setIsVerified((byte) 1);
            otpRepository.deleteById(otp.getId());
            userRepository.save(user);
            return "Email Id verified Successfully";
        }

        return "Invalid OTP! Please try again.";
    }

    @GetMapping("/resend-code/{emailId}")
    public Boolean resendCode(@PathVariable String emailId){
        User user = userRepository.findByEmail(emailId);
        if(user.getId() < 0) throw new UserNotFoundException("emailId - " + emailId);
        Otp otp = otpRepository.findByUser(user);
        String newOtp = ExtraTools.generateOtp();
        otp.setOtp(newOtp);
        otp.setUser(user);
        otp.setCreatedAt(new Date());
        otpRepository.save(otp);
        //tools.sendOtpToEmail(emailId, newOtp);
        return true;
    }

    @GetMapping("/otp-list")
    public List<Otp> getOtpList(){
        return otpRepository.findAll();
    }
}
