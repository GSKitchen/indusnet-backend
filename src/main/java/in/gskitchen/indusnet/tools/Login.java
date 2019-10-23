package in.gskitchen.indusnet.tools;

/**
 * This Class for receiving JSON Object from User login page
 */
public class Login {
    private String email;
    private String password;

    public Login() {
    }

    /**
     * This constructor need two parameter, Email and Password.
     * @param email as String
     * @param password as String
     */
    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
