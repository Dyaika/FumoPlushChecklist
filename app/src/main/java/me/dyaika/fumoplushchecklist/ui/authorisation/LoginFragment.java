package me.dyaika.fumoplushchecklist.ui.authorisation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import me.dyaika.fumoplushchecklist.MainActivity;
import me.dyaika.fumoplushchecklist.R;
import me.dyaika.fumoplushchecklist.model.AccountViewModel;

public class LoginFragment extends Fragment {
    private ActionBar actionBar;
    private Button login_button;
    private View signup_text_button;
    private NavController navController;
    private TextInputEditText login_text;
    private TextInputEditText password_text;
    private AccountViewModel accountViewModel;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountViewModel = ((MainActivity)requireActivity())
                .getViewModelProvider()
                .get(AccountViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        ((MainActivity)activity).setNavVisibility(false);/*
        actionBar = activity.getSupportActionBar(); // или Toolbar toolbar = activity.findViewById(R.id.toolbar);
        if (actionBar != null) {
            actionBar.setTitle("Log in");
        }*/
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        login_button = view.findViewById(R.id.login_button);
        signup_text_button = view.findViewById(R.id.signup_text_button);
        login_text = view.findViewById(R.id.login_text);
        password_text = view.findViewById(R.id.password_text);
        buttonsConfig();
    }

    public void buttonsConfig(){
        login_button.setOnClickListener(view -> {
            String password = password_text.getText().toString();
            String login = login_text.getText().toString();
            accountViewModel.checkAuthentication(login, password);
            if (Boolean.TRUE.equals(accountViewModel.isLoggedIn().getValue())){
                navController.navigate(R.id.action_loginFragment_to_navigation_profile);
                Toast.makeText(getContext(), "Успешно", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getContext(), "Неверные данные", Toast.LENGTH_SHORT).show();
            }
        });
        signup_text_button.setOnClickListener(view -> {
            navController.navigate(R.id.action_loginFragment_to_signupFragment);
        });
    }
}