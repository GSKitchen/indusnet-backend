package in.gskitchen.indusnet;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.gskitchen.indusnet.exceptions.UserNotFoundException;
import in.gskitchen.indusnet.model.Company;
import in.gskitchen.indusnet.model.Otp;
import in.gskitchen.indusnet.model.Signer;
import in.gskitchen.indusnet.model.User;
import in.gskitchen.indusnet.repository.CompanyRepository;
import in.gskitchen.indusnet.repository.OtpRepository;
import in.gskitchen.indusnet.repository.SignerRepository;
import in.gskitchen.indusnet.repository.UserRepository;
import in.gskitchen.indusnet.tools.ExtraTools;
import in.gskitchen.indusnet.tools.VerifyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private SignerRepository signerRepository;

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
        String newOtp = ExtraTools.generateOtp();
        Otp otp = new Otp();
        otp.setOtp(newOtp);
        otp.setUser(savedUser);
        otpRepository.save(otp);
        try {
            tools.sendOtpToEmail(savedUser.getEmail(), newOtp);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/verify-email")
    public String verifyEmail(@RequestBody VerifyUser verifyUser){
        Optional<User> userOptional = userRepository.findByEmail(verifyUser.getEmail());
        User user = userOptional.get();
        if(!userOptional.isPresent()) throw new UserNotFoundException("Email - " + verifyUser.getEmail());
        Otp otp = otpRepository.findByUser(user);
        if(otp.getOtp().equals(verifyUser.getUserOtp())){
            user.setIsVerified((byte) 1);
            otpRepository.deleteById(otp.getId());
            userRepository.save(user);
            return "Email Id verified Successfully";
        }

        return "Invalid OTP! Please try again.";
    }

    @GetMapping("/resend-code/{emailId}")
    public Boolean resendCode(@PathVariable String emailId){
        Optional<User> userOptional = userRepository.findByEmail(emailId);
        User user = userOptional.get();
        if(!userOptional.isPresent()) throw new UserNotFoundException("Email - " + emailId);
        Otp otp = otpRepository.findByUser(user);
        String newOtp = ExtraTools.generateOtp();
        otp.setOtp(newOtp);
        otp.setUser(user);
        otp.setCreatedAt(new Date());
        otpRepository.save(otp);
        try {
            tools.sendOtpToEmail(emailId, newOtp);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @PostMapping("/save-company/{email}")
    public void createCompany(@PathVariable String email, @RequestBody Company company){
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.get();
        if(!userOptional.isPresent()) throw new UserNotFoundException("email - " + email);
        company.setUser(user);
        companyRepository.save(company);
    }

    @PostMapping("/save-signer/{email}")
    public User createSignerDetails(@PathVariable String email, @RequestBody Signer signer){
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.get();
        if(!userOptional.isPresent()) throw new UserNotFoundException("email - " + email);
        signer.setUser(user);
        signerRepository.save(signer);
        return user;
    }

    @GetMapping("/companies")
    public List<Company> companyList(){
        return companyRepository.findAll();
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody String emailAndPassword){
        String[] userInfo = emailAndPassword.trim().split(",");
        Optional<User> userOptional = userRepository.findByEmail(userInfo[0]);
        User user = userOptional.get();
        if(!userOptional.isPresent()) throw new UserNotFoundException("Email - " + userInfo[0]);
        if(user.getPassword().equals(userInfo[1])){
            return user;
        }
        return null;
    }
}
