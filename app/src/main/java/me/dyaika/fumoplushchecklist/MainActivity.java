package me.dyaika.fumoplushchecklist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Map;

import me.dyaika.fumoplushchecklist.databinding.ActivityMainBinding;
import me.dyaika.fumoplushchecklist.logic.AccountViewModel;
import me.dyaika.fumoplushchecklist.logic.ItemsViewModel;
import me.dyaika.fumoplushchecklist.logic.MySecurity;
import me.dyaika.fumoplushchecklist.pojo.User;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AccountViewModel accountViewModel;
    private ItemsViewModel itemsViewModel;
    private ViewModelProvider viewModelProvider;
    SharedPreferences sharedPreferences;
    private boolean logged_in;
    private User user;
    public ViewModelProvider getViewModelProvider() {
        initViewModelProvider();
        return viewModelProvider;
    }
    private void initViewModelProvider() {
        if (viewModelProvider == null){
            viewModelProvider = new ViewModelProvider(this);
        }
    }

    private void prepareSharedPreferences(){
        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
    }
    public void saveAuthorisation(String login, String firstname, String lastname){
        if (sharedPreferences == null){
            prepareSharedPreferences();
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LOGIN", login);
        editor.putString("FIRSTNAME", firstname);
        editor.putString("LASTNAME", lastname);
        editor.apply();
    }
    public void removeAuthorisation(){
        if (sharedPreferences == null){
            prepareSharedPreferences();
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("LOGIN");
        editor.remove("FIRSTNAME");
        editor.remove("LASTNAME");
        editor.apply();
    }
    public void authorise(){
        if (sharedPreferences == null){
            prepareSharedPreferences();
        }
        String login = sharedPreferences.getString("LOGIN", null);
        String firstname = sharedPreferences.getString("FIRSTNAME", null);
        String lastname = sharedPreferences.getString("LASTNAME", null);
        if (login != null && lastname != null && firstname != null){
            accountViewModel.connectLocalUserToRemote(login, firstname, lastname);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        initViewModelProvider();
        accountViewModel = viewModelProvider.get(AccountViewModel.class);
        itemsViewModel = viewModelProvider.get(ItemsViewModel.class);
        itemsViewModel.loadItems(this);
        accountViewModel.getLoadingComplete().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    System.out.println(accountViewModel.getEncryptedUsersLiveData().getValue().get(-1317312109).getFavorite().toString());
                    authorise();

                }
            }
        });

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_list, R.id.navigation_collection, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_list) {
                navController.navigate(R.id.navigation_list);
            } else if (itemId == R.id.navigation_collection) {
                navController.navigate(R.id.navigation_collection);
            } else if (itemId == R.id.navigation_profile) {
                navController.navigate(R.id.navigation_profile);
            }

            return true;
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        accountViewModel.updateReference();
    }

    @Override
    protected void onResume() {
        super.onResume();


        DatabaseReference ref = accountViewModel.getDatabaseReference();

    }

    public void setNavVisibility(boolean visibile){

        if (visibile){
            findViewById(R.id.nav_view).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.nav_view).setVisibility(View.GONE);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {

            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}