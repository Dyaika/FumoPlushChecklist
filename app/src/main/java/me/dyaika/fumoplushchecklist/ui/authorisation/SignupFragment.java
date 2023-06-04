package me.dyaika.fumoplushchecklist.ui.authorisation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import me.dyaika.fumoplushchecklist.MainActivity;
import me.dyaika.fumoplushchecklist.R;
import me.dyaika.fumoplushchecklist.model.AccountViewModel;

public class SignupFragment extends Fragment {
    private static final String TAG = "signup fragment";
    private ActionBar actionBar;

    private Button signup_button;
    private View login_text_button;
    private TextInputEditText firstname_text;
    private TextInputEditText lastname_text;
    private TextInputEditText login_text;
    private TextInputEditText password1_text;
    private TextInputEditText password2_text;
    private NavController navController;
    private AccountViewModel accountViewModel;
    public SignupFragment() {
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
        ((MainActivity)activity).setNavVisibility(false);
        actionBar = activity.getSupportActionBar(); // или Toolbar toolbar = activity.findViewById(R.id.toolbar);
        if (actionBar != null) {
            actionBar.setTitle("Sign up");
        }
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        signup_button = view.findViewById(R.id.signup_button);
        login_text_button = view.findViewById(R.id.login_text_button);

        firstname_text = view.findViewById(R.id.firstname_text);
        lastname_text = view.findViewById(R.id.lastname_text);
        login_text = view.findViewById(R.id.login_text);
        password1_text = view.findViewById(R.id.password1_text);
        password2_text = view.findViewById(R.id.password2_text);
        buttonsConfig();
    }

    public void buttonsConfig(){
        signup_button.setOnClickListener(view -> {
            String firstname = firstname_text.getText().toString();
            String lastname = lastname_text.getText().toString();
            String login = login_text.getText().toString();
            String password1 = password1_text.getText().toString();
            String password2 = password2_text.getText().toString();

            if (AccountViewModel.checkRegistration(firstname, lastname, login, password1, password2)){
                accountViewModel.checkAuthentication(login, password1);
                if (Boolean.TRUE.equals(accountViewModel.isLoggedIn().getValue())){
                    navController.navigate(R.id.action_signupFragment_to_navigation_profile);
                    Toast.makeText(getContext(), "Успешно", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Непредвиденная ошибка", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (!AccountViewModel.checkNewUserdata(firstname, lastname)){
                    Toast.makeText(getContext(), "Неподходящая длина имени/фамилии", Toast.LENGTH_SHORT).show();
                }
                if (!AccountViewModel.checkNewPassword(password1, password2)){
                    Toast.makeText(getContext(), "Неподходящий пароль", Toast.LENGTH_SHORT).show();
                }
                if (!AccountViewModel.checkNewLogin(login)){
                    Toast.makeText(getContext(), "Такой логин уже есть", Toast.LENGTH_SHORT).show();
                }
            }

        });
        login_text_button.setOnClickListener(view -> {
            navController.navigate(R.id.action_signupFragment_to_loginFragment);
        });
    }
}