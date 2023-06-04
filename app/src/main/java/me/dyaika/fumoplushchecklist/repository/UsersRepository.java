package me.dyaika.fumoplushchecklist.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.dyaika.fumoplushchecklist.pojo.Item;
import me.dyaika.fumoplushchecklist.pojo.User;

public class UsersRepository {
    private static Map<Integer, User> users;
    public static void loadUsers(){
        users = new HashMap<>();
        users.put(-1317312109, new User(
                "8m68aNkg1C0Ac+Vu6hPznp9T6KwohPLtME12aa2n/Cw=\n",
                "yopg3SNiCNdHKw0yOCbCxRGm+4TpUJ+4GDOAYet0tl0=\n",
                "keFQmWI8RXtWrdFCu6d+1Q==",
                -1697216807));
        Set<Integer> favorite = new HashSet<>();
        favorite.add(0);
        users.get(-1317312109).setFavorite(favorite);
    }

    public static User getEncryptedUserByKey(int key){
        if (users == null){
            loadUsers();
        }
        return users.get(key);
    }
    public static void addEncryptedUser(int key, User user){
        users.put(key, user);
    }
}


