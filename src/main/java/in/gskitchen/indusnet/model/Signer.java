package in.gskitchen.indusnet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Signer {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @Size(min = 3, message = "First Name should be at least 3 characters")
    private String firstName;

    @NotNull
    @Size(min = 3, message = "Last name should be at least 3 characters")
    private String lastName;

    @NotNull
    private String streetAddress;

    @NotNull
    private String country;

    @NotNull
    private String state;

    @NotNull
    private String city;

    @NotNull
    private String zip;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    private Date createdAt = new Date();

    public Signer() {
        super();
    }

    public Signer(Integer id, String firstName, String lastName, String streetAddress, String country, String state, String city, String zip, Date dob, User user, java.util.Date createdAt) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddress = streetAddress;
        this.country = country;
        this.state = state;
        this.city = city;
        this.zip = zip;
        this.dob = dob;
        this.user = user;
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

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Signer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", dob=" + dob +
                ", user=" + user +
                ", createdAt=" + createdAt +
                '}';
    }
}
