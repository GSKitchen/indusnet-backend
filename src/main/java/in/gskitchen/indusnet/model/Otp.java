package in.gskitchen.indusnet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Otp {

    @Id
    @GeneratedValue
    private Integer id;

    private String otp;

    private Byte isValid = 1;

    private Date createdAt = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public Otp() {
        super();
    }

    public Otp(Integer id, String otp, Byte isValid, Date createdAt, User user) {
        super();
        this.id = id;
        this.otp = otp;
        this.isValid = isValid;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Byte getIsValid() {
        return isValid;
    }

    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
