package me.dyaika.fumoplushchecklist.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import me.dyaika.fumoplushchecklist.MainActivity;
import me.dyaika.fumoplushchecklist.R;
import me.dyaika.fumoplushchecklist.logic.AccountViewModel;

public class ProfileFragment extends Fragment {
    private boolean logged_in = false;

    private NavController navController;
    private Button exit_button;
    private Button login_button;
    private Button signup_button;
    private TextView username_text;
    private AccountViewModel accountViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountViewModel = ((MainActivity)requireActivity())
                .getViewModelProvider()
                .get(AccountViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        logged_in = Boolean.TRUE.equals(accountViewModel.isLoggedIn().getValue());
        // Two different layouts for user who is logged in and not
        if (logged_in){
            return inflater.inflate(R.layout.fragment_user_profile, container, false);
        } else {
            return inflater.inflate(R.layout.fragment_empty_profile, container, false);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        ((MainActivity)activity).setNavVisibility(true);

        navController = Navigation.findNavController(view);
        if (logged_in){
            exit_button = view.findViewById(R.id.exit_button);
            username_text = view.findViewById(R.id.username_text);
        } else {
            login_button = view.findViewById(R.id.login_button);
            signup_button = view.findViewById(R.id.signup_button);
        }

        contentInflation();
    }

    public void contentInflation(){

        if (logged_in){
            exit_button.setOnClickListener(view -> {
                accountViewModel.logout();
                navController.navigate(R.id.action_navigation_profile_to_loginFragment);

            });
            username_text.setText(accountViewModel.getUser().getValue().getFirstname() + " " +
                    accountViewModel.getUser().getValue().getLastname());
        }
        else{
            login_button.setOnClickListener(view -> {
                navController.navigate(R.id.action_navigation_profile_to_loginFragment);
            });
            signup_button.setOnClickListener(view -> {
                navController.navigate(R.id.action_navigation_profile_to_signupFragment);
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}