package me.dyaika.fumoplushchecklist.logic;



import static me.dyaika.fumoplushchecklist.logic.MySecurity.decrypt;
import static me.dyaika.fumoplushchecklist.logic.MySecurity.encrypt;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import me.dyaika.fumoplushchecklist.pojo.FBUser;
import me.dyaika.fumoplushchecklist.pojo.User;

public class AccountViewModel extends ViewModel {
    private static final String TAG = "account view-model";
    private MutableLiveData<Boolean> loading_complete = new MutableLiveData<Boolean>(false);
    private MutableLiveData<User> user = new MutableLiveData<>(null);
    private MutableLiveData<Map<Integer, User>> encryptedUsersLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> logged_in = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> isLoggedIn() {
        return logged_in;
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    private DatabaseReference databaseReference;

    public void updateReference() {
        // Получите ссылку на базу данных Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<Integer, User> userMap = new HashMap<>();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        Integer userId = Integer.parseInt(userSnapshot.getKey());
                        FBUser userData = userSnapshot.getValue(FBUser.class);
                        userMap.put(userId, new User(userData));
                    }
                }
                encryptedUsersLiveData.postValue(userMap);
                loading_complete.postValue(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок
                System.out.println("Чтение данных было отменено: " + databaseError.getMessage());
            }
        });
    }
    public DatabaseReference getDatabaseReference(){
        return databaseReference;
    }

    public void createUser(User user, int key) {
        // Создание нового пользователя в Firebase
        String userId = databaseReference.push().getKey();
        databaseReference.child(String.valueOf(key)).setValue(new FBUser(user));
    }

    public void updateUser(User user, int key) {
        // Обновление данных пользователя в Firebase
        databaseReference.child(String.valueOf(key)).setValue(new FBUser(user));
    }

    public LiveData<Map<Integer, User>> getEncryptedUsersLiveData() {
        return encryptedUsersLiveData;
    }

    public LiveData<Boolean> getLoadingComplete() {
        return loading_complete;
    }

    public void deleteUser(int key) {
        // Удаление пользователя из Firebase
        databaseReference.child(String.valueOf(key)).removeValue();
    }

    public void logout(){
        user.setValue(null);
        logged_in.setValue(false);
    }

    public void checkAuthentication(String login, String password){
        if (password.length() >= 8 && password.length() <= 16){
            String key = password + password.toUpperCase();
            String encryptedPassword = encrypt(password, key);
            if (encryptedUsersLiveData.getValue() != null){
                User encryptedUser = encryptedUsersLiveData.getValue().get(login.hashCode());
                if (encryptedUser != null){
                    logged_in.setValue(encryptedPassword.hashCode() == encryptedUser.getPassword());
                    if (Boolean.TRUE.equals(logged_in.getValue())){
                        User user1 =new User(
                                decrypt(encryptedUser.getFirstname(), key),
                                decrypt(encryptedUser.getLastname(), key),
                                decrypt(encryptedUser.getLogin(), key),
                                encryptedUser.getPassword());
                        user1.setFavorite(encryptedUser.getFavorite());
                        user.postValue(user1);//!
                    }
                }
            }

        }
    }
    public void connectLocalUserToRemote(String login, String firstname, String lastname){
        if (encryptedUsersLiveData.getValue() != null){
            User user1 = encryptedUsersLiveData.getValue().get(login.hashCode());
            if (user1 != null){
                Log.d("account", user1.getFavorite().toString());
                User decryptedUser = new User(
                        firstname,
                        lastname,
                        login,
                        user1.getPassword());
                decryptedUser.setFavorite(user1.getFavorite());
                user.postValue(decryptedUser);
                logged_in.postValue(true);
            }
        }
    }

    public  boolean checkNewLogin(String login){
        if (encryptedUsersLiveData.getValue() != null){
            return !encryptedUsersLiveData.getValue().containsKey(login.hashCode());

        }
        return false;
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

    public boolean checkRegistration(String firstname, String lastname, String login,
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
            //UsersRepository.addEncryptedUser(login.hashCode(), user_encoded);
            Map<Integer, User> userstemp = encryptedUsersLiveData.getValue();
            userstemp.put(login.hashCode(), user_encoded);
            encryptedUsersLiveData.postValue(userstemp);
            createUser(user_encoded, login.hashCode());
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
            if (encryptedUsersLiveData.getValue() != null){
                User tempuser = encryptedUsersLiveData.getValue().get(user.getValue().getLogin().hashCode());
                if (tempuser != null){
                    tempuser.setFavorite(user.getValue().getFavorite());
                    updateUser(tempuser, user.getValue().getLogin().hashCode());
                }

            }
        }
    }

}
