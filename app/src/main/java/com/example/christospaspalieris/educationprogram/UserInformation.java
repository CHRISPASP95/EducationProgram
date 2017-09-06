package com.example.christospaspalieris.educationprogram;

/**
 * Created by Christos Paspalieris on 22-Apr-17.
 */

public class UserInformation {

    public String username;
    public String first_name;
    public String last_name;
    public String email;
    public String password;
    public String age;
    public String sex;
    public String image;

    public UserInformation(){

    }

    public UserInformation(String username, String first_name, String last_name, String email, String password,String age,String sex, String image) {
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.sex = sex;
        this.image = image;
    }



    public String getUsername() {
        return username;
    }

    public String getAge() { return age; }

    public String getFirstName(){ return first_name; }

    public String getLastName(){ return last_name; }

    public String getPassword(){ return password; }

    public String getImage() { return image; }
}
