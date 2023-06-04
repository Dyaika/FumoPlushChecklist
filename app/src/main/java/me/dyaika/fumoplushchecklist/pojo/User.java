package me.dyaika.fumoplushchecklist.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class User {
    private String firstname;
    private String lastname;
    private String login;
    private Set<Integer> favorite;

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getLogin() {
        return login;
    }

    private int password;

    public int getPassword() {
        return password;
    }

    public User(String firstname, String lastname, String login, int password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
        this.favorite = new HashSet<>();
    }

    public Set<Integer> getFavorite() {
        return favorite;
    }

    public void setFavorite(Set<Integer> favorite) {
        favorite.clear();
        this.favorite.addAll(favorite);
    }
}
