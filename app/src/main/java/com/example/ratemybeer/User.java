package com.example.ratemybeer;

import java.util.HashMap;

public class User {
    public String fullName, age, email, password,pseudo ;
    public HashMap<String,String> rated_beers;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public HashMap<String, String> getRated_beers() {
        return rated_beers;
    }

    public void setRated_beers(HashMap<String, String> rated_beers) {
        this.rated_beers = rated_beers;
    }


    public User(){

    }

    public User(String fullName, String age , String email, String password,String pseudo) {
        this.fullName=fullName;
        this.age=age;
        this.email=email ;
        this.password=password ;
        this.pseudo=pseudo ;
        rated_beers = new HashMap<>();
    }
}
