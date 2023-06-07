package me.dyaika.fumoplushchecklist.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class User implements Serializable {
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
    public User(FBUser user){
        copyFBUser(user);
    }

    public Set<Integer> getFavorite() {
        return favorite;
    }

    public void setFavorite(Set<Integer> favorite) {
        this.favorite.clear();
        if (favorite != null){
            this.favorite.addAll(favorite);
        }
    }
    public void copyFBUser(FBUser user){
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.favorite = new HashSet<>();
        this.favorite.addAll(user.getFavorite());
    }
}
