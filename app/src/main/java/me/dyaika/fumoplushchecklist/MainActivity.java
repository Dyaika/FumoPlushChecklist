package me.dyaika.fumoplushchecklist;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        initViewModelProvider();
        accountViewModel = viewModelProvider.get(AccountViewModel.class);
        itemsViewModel = viewModelProvider.get(ItemsViewModel.class);

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

        accountViewModel.createUser(new User(
                MySecurity.encrypt("Александр", "qwertyuiopQWERTYUIOP"),
                MySecurity.encrypt("Разшильдяев", "qwertyuiopQWERTYUIOP"),
                MySecurity.encrypt("dyaika", "qwertyuiopQWERTYUIOP"),
                MySecurity.encrypt("qwertyuiop", "qwertyuiopQWERTYUIOP").hashCode()), "dyaika".hashCode());

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