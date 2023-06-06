package me.dyaika.fumoplushchecklist.model;



import static me.dyaika.fumoplushchecklist.model.MySecurity.decrypt;
import static me.dyaika.fumoplushchecklist.model.MySecurity.encrypt;
import static me.dyaika.fumoplushchecklist.repository.UsersRepository.getEncryptedUserByKey;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import me.dyaika.fumoplushchecklist.pojo.User;
import me.dyaika.fumoplushchecklist.repository.UsersRepository;

public class AccountViewModel extends ViewModel {
    private static final String TAG = "account view-model";
    private MutableLiveData<User> user = new MutableLiveData<>(null);
    private MutableLiveData<Boolean> logged_in = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> isLoggedIn() {
        return logged_in;
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public void logout(){
        user.setValue(null);
        logged_in.setValue(false);
    }

    public void checkAuthentication(String login, String password){
        if (password.length() >= 8 && password.length() <= 16){
            String key = password + password.toUpperCase();
            String encryptedPassword = encrypt(password, key);
            User encryptedUser = getEncryptedUserByKey(login.hashCode());
            if (encryptedUser != null){
                logged_in.setValue(encryptedPassword.hashCode() == encryptedUser.getPassword());
                if (Boolean.TRUE.equals(logged_in.getValue())){
                    user.setValue(new User(
                            decrypt(encryptedUser.getFirstname(), key),
                            decrypt(encryptedUser.getLastname(), key),
                            decrypt(encryptedUser.getLogin(), key),
                            encryptedUser.getPassword()));
                }
            }
        }
    }

    public static boolean checkNewLogin(String login){
        return getEncryptedUserByKey(login.hashCode()) == null;
    }
    public static boolean checkNewPassword(String password1, String password2){
        return (password1.equals(password2) &&
                password1.length() >= 8 &&
                password1.length() <= 16);
    }
    public static boolean checkNewUserdata(String firstname, String lastname){
        return (firstname.length() > 1 &&
                firstname.length() < 20 &&
                lastname.length() > 1 &&
                lastname.length() < 20);
    }

    public static boolean checkRegistration(String firstname, String lastname, String login,
                                            String password1, String password2){
        if (checkNewLogin(login) &&
                checkNewPassword(password1, password2) &&
                checkNewUserdata(firstname, lastname)){
            String key = password1 + password1.toUpperCase();
            User user_encoded = new User(
                    encrypt(firstname, key),
                    encrypt(lastname, key),
                    encrypt(login, key),
                    encrypt(password1, key).hashCode());
            UsersRepository.addEncryptedUser(login.hashCode(), user_encoded);
            return true;
        }
        return false;
    }
    public boolean isFavorite(int item_id){
        if (user.getValue() != null){
            return user.getValue().getFavorite().contains(item_id);
        }
        return false;
    }
    public void setFavorite(int item_id, boolean favorite){
        if (user.getValue() != null){
            if (favorite){
                user.getValue().getFavorite().add(item_id);

            } else {
                user.getValue().getFavorite().remove(item_id);

            }
        }
    }
}
