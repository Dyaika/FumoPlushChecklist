package me.dyaika.fumoplushchecklist.pojo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FBUser implements Serializable {
    private String firstname;
    private String lastname;
    private String login;
    private List<Integer> favorite;

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

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setFavorite(List<Integer> favorite) {
        this.favorite = favorite;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public FBUser() {
        this.favorite = new ArrayList<>();
    }

    public FBUser(String firstname, String lastname, String login, int password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
        this.favorite = new ArrayList<>();
    }
    public FBUser(User user){
        copyUser(user);
    }

    public List<Integer> getFavorite() {
        return favorite;
    }

    public void copyUser(User user){
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.favorite = new ArrayList<>();
        this.favorite.addAll(user.getFavorite());
    }
}
