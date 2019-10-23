package in.gskitchen.indusnet;

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
import in.gskitchen.indusnet.tools.Login;
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
import java.util.Optional;

/**
 * This class contain all method to handle GET, POST, PUT, DELETE request.
 * <p><ul>
 *     <li>To retrieve {@link User} details by id go for {@link #retrieveUser(int id)} method</li>
 *     <li>To add a new {@link User}, use {@link #createUser(User user)}</li>
 *     <li>To verify email, use {@link #verifyEmail(VerifyUser verifyUser)}</li>
 *     <li>To resend verification code, use {@link #resendCode(String email)}</li>
 *     <li>To save company details use {@link #createCompany(String, Company)}</li>
 *     <li>To save Authorised signer details use {@link #createSignerDetails(String, Signer)}</li>
 *     <li>To do login, use {@link #loginUser(Login)} method</li>
 * </ul></p>
 *
 */
@CrossOrigin(origins = "*", allowedHeaders = "*") /** Make this entire controller accessible from another Machine */
@RestController /** Make this class as Rest Controller */
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


    /**
     * This function is used to get User Info by ID as @PathVariable.
     * @param id as number
     * @return Optional<User>
     */
    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> retrieveUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        //If User not available on this id, throw this
        if(!user.isPresent()) throw new UserNotFoundException("id - " + id);

        return user;
    }

    /**
     * This function made for save new {@link User} in database.
     * @param user
     * @return ResponseEntity<Object> with saved user Id.
     */
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody User user){
        //Save user
        User savedUser = userRepository.save(user);

        //Generate OTP & send email to User
        String newOtp = ExtraTools.generateOtp();
        Otp otp = new Otp();
        otp.setOtp(newOtp);
        otp.setUser(savedUser);
        otpRepository.save(otp);
        try {
            //sending mail
            tools.sendOtpToEmail(savedUser.getEmail(), newOtp);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //building URL of the saved user with id
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * After save {@link User} information, use this method to verify email.
     * @param verifyUser of {@link VerifyUser}
     * @return String
     */
    @PostMapping("/verify-email")
    public String verifyEmail(@RequestBody VerifyUser verifyUser){
        //get User by email id
        Optional<User> userOptional = userRepository.findByEmail(verifyUser.getEmail());
        User user = userOptional.get();
        if(!userOptional.isPresent()) throw new UserNotFoundException("Email - " + verifyUser.getEmail());

        //retrieve User's otp from database
        Otp otp = otpRepository.findByUser(user);
        //verifying OTP
        if(otp.getOtp().equals(verifyUser.getUserOtp())){
            user.setIsVerified((byte) 1);
            otpRepository.deleteById(otp.getId());
            userRepository.save(user);
            return "Email Id verified Successfully";
        }

        return "Invalid OTP! Please try again.";
    }

    /**
     * In case if The {@link User} didnt received Email, send again with new OTP
     * @param emailId as String
     * @return Boolean
     */
    @GetMapping("/resend-code/{emailId}")
    public Boolean resendCode(@PathVariable String emailId){
        //get User by email id
        Optional<User> userOptional = userRepository.findByEmail(emailId);
        User user = userOptional.get();
        if(!userOptional.isPresent()) throw new UserNotFoundException("Email - " + emailId);

        Otp otp = otpRepository.findByUser(user);

        //Generating new OTP
        String newOtp = ExtraTools.generateOtp();
        otp.setOtp(newOtp);
        otp.setUser(user);
        otp.setCreatedAt(new Date());
        otpRepository.save(otp);

        //sending mail
        try {
            tools.sendOtpToEmail(emailId, newOtp);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * This method used to save company information.
     * @param email as String with path variable &
     * @param company as {@link Company} object
     */
    @PostMapping("/save-company/{email}")
    public void createCompany(@PathVariable String email, @RequestBody Company company){
        //get User by email id
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.get();
        if(!userOptional.isPresent()) throw new UserNotFoundException("email - " + email);
        //set User to save company info
        company.setUser(user);

        //save company data to DB
        companyRepository.save(company);
    }

    /**
     * Use this method to save Authorised Signer Info.
     * @param email as String
     * @param signer as {@link Signer} object
     */
    @PostMapping("/save-signer/{email}")
    public void createSignerDetails(@PathVariable String email, @RequestBody Signer signer){
        //get User by email id
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.get();
        if(!userOptional.isPresent()) throw new UserNotFoundException("email - " + email);

        //set user to Signer info
        signer.setUser(user);

        //save data to DB
        signerRepository.save(signer);
    }


    /**
     * This method is used to login {@link User} using username and password.
     * @param login as {@link Login} object
     * @return {@link User}
     */
    @PostMapping("/login")
    public User loginUser(@RequestBody Login login){
        //get User by email id
        Optional<User> userOptional = userRepository.findByEmail(login.getEmail());
        User user = userOptional.get();
        if(!userOptional.isPresent()) throw new UserNotFoundException("Email - " + login.getEmail());
        //verify user, and send data back
        if(user.getPassword().equals(login.getPassword())){
            return user;
        }
        return null;
    }

    @GetMapping("/users")
    public List<User> userList(){
        return userRepository.findAll();
    }
}
