package Domain;

import java.util.Date;

public class Student {
    private String email;
    private String name;
    private Date birthDate;
    private String gender;
    private String adress;
    private String city;
    private String country;


    public Student(String email, String name, Date date, String gender, String adress, String city, String country) {
        this.email = email;
        this.name = name;
        this.birthDate = date;
        this.gender = gender;
        this.adress = adress;
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return "{" +
            " email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", gender='" + getGender() + "'" +
            ", adress='" + getAdress() + "'" +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
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

    public String getAdress() {
        return this.adress;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

}
