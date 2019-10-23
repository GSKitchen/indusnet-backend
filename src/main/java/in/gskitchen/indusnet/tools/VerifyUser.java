package in.gskitchen.indusnet.tools;

/**
 * This class to receive Email and OTP from client side as JSON Obejct
 */
public class VerifyUser {
    private String userOtp;
    private String email;

    public VerifyUser() {
    }

    public VerifyUser(String userOtp, String email) {
        this.userOtp = userOtp;
        this.email = email;
    }

    public String getUserOtp() {
        return userOtp;
    }

    public void setUserOtp(String userOtp) {
        this.userOtp = userOtp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
