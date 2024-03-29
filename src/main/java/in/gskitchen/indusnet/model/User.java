package in.gskitchen.indusnet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @Size(min = 3, message = "Fist Name should more than 3 characters")
    private String firstName;

    @NotNull
    @Size(min = 3, message = "Last Name should more than 3 characters")
    private String lastName;

    @Column(unique = true)
    private String email;

    @NotNull
    private String companyName;

    @NotNull
    @Size(min = 6, message = "Password should be 6 characters")
    private String password;

    private Byte isVerified = 0;

    private Date createdAt = new Date();

    public User() {
        super();
    }

    public User(@NotNull @Size(min = 3, message = "Fist Name should more than 3 characters") String firstName, @NotNull @Size(min = 3, message = "Last Name should more than 3 characters") String lastName, String email, @NotNull String companyName, @NotNull @Size(min = 6, message = "Password should be 6 characters") String password, Byte isVerified, Date createdAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.companyName = companyName;
        this.password = password;
        this.isVerified = isVerified;
        this.createdAt = createdAt;
    }

    public User(Integer id, String firstName, String lastName, String email, String companyName, String password, Byte isVerified, Date createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.companyName = companyName;
        this.password = password;
        this.isVerified = isVerified;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Byte isVerified) {
        this.isVerified = isVerified;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
