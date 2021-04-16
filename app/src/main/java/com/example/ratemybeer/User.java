package com.example.ratemybeer;

import java.util.HashMap;

public class User {
    public String firstName, lastName, age, email, pseudo;
    private Boolean isAdmin;
    public HashMap<String,String> rated_beers;

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

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Boolean getAdmin() { return isAdmin; }

    public void setAdmin(Boolean admin) { isAdmin = admin; }

    public HashMap<String, String> getRated_beers() {
        return rated_beers;
    }

    public void setRated_beers(HashMap<String, String> rated_beers) {
        this.rated_beers = rated_beers;
    }

    public User(String firstName, String lastName, String age , String email,String pseudo) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.pseudo=pseudo ;
        this.age=age;
        this.email=email ;
        rated_beers = new HashMap<>();
    }
}
