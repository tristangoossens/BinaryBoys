package Domain;

import java.util.Date;

public class Student {
    private String email;
    private String name;
    private Date birthDate;
    private String gender;
    private String address;
    private String city;
    private String country;


    public Student(String email, String name, Date date, String gender, String address, String city, String country) {
        this.email = email;
        this.name = name;
        this.birthDate = date;
        this.gender = gender;
        this.address = address;
        this.city = city;
        this.country = country;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public String getGender() {
        return this.gender;
    }

    public String getAddress() {
        return this.address;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "{" +
            " email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", gender='" + getGender() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }

}
