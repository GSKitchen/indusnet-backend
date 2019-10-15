package in.gskitchen.indusnet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Company {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @Size(min = 4, message = "Company name should be at least 4 characters long")
    private String companyName;

    @NotNull
    private String companyId;

    @NotNull
    private String companyStreet;

    @NotNull
    private String companyCountry;

    @NotNull
    private String companyState;

    @NotNull
    private String companyCity;

    @NotNull
    private String companyZip;

    @NotNull
    private String companyPhone;

    @NotNull
    private String companyWeb;

    @NotNull
    @Past
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date companyInco;

    @NotNull
    private String companyIncoState;

    @NotNull
    private String companyIncoCity;

    @NotNull
    private Date createdAt = new Date();

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public Company() {
        super();
    }

    public Company(Integer id, String companyName, String companyId, String companyStreet, String companyCountry, String companyState, String companyCity, String companyZip, String companyPhone, String companyWeb, @Past Date companyInco, String companyIncoState, String companyIncoCity, Date createdAt, User user) {
        super();
        this.id = id;
        this.companyName = companyName;
        this.companyId = companyId;
        this.companyStreet = companyStreet;
        this.companyCountry = companyCountry;
        this.companyState = companyState;
        this.companyCity = companyCity;
        this.companyZip = companyZip;
        this.companyPhone = companyPhone;
        this.companyWeb = companyWeb;
        this.companyInco = companyInco;
        this.companyIncoState = companyIncoState;
        this.companyIncoCity = companyIncoCity;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyStreet() {
        return companyStreet;
    }

    public void setCompanyStreet(String companyStreet) {
        this.companyStreet = companyStreet;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public void setCompanyCountry(String companyCountry) {
        this.companyCountry = companyCountry;
    }

    public String getCompanyState() {
        return companyState;
    }

    public void setCompanyState(String companyState) {
        this.companyState = companyState;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyZip() {
        return companyZip;
    }

    public void setCompanyZip(String companyZip) {
        this.companyZip = companyZip;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyWeb() {
        return companyWeb;
    }

    public void setCompanyWeb(String companyWeb) {
        this.companyWeb = companyWeb;
    }

    public Date getCompanyInco() {
        return companyInco;
    }

    public void setCompanyInco(Date companyInco) {
        this.companyInco = companyInco;
    }

    public String getCompanyIncoState() {
        return companyIncoState;
    }

    public void setCompanyIncoState(String companyIncoState) {
        this.companyIncoState = companyIncoState;
    }

    public String getCompanyIncoCity() {
        return companyIncoCity;
    }

    public void setCompanyIncoCity(String companyIncoCity) {
        this.companyIncoCity = companyIncoCity;
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

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", companyId='" + companyId + '\'' +
                ", companyStreet='" + companyStreet + '\'' +
                ", companyCountry='" + companyCountry + '\'' +
                ", companyState='" + companyState + '\'' +
                ", companyCity='" + companyCity + '\'' +
                ", companyZip='" + companyZip + '\'' +
                ", companyPhone='" + companyPhone + '\'' +
                ", companyWeb='" + companyWeb + '\'' +
                ", companyInco=" + companyInco +
                ", companyIncoState='" + companyIncoState + '\'' +
                ", companyIncoCity='" + companyIncoCity + '\'' +
                ", createdAt=" + createdAt +
                ", user=" + user +
                '}';
    }
}
