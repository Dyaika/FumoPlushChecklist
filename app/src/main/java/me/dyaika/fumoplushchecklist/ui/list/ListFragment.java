package me.dyaika.fumoplushchecklist.ui.list;

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
import androidx.lifecycle.ViewModelProvider;

import me.dyaika.fumoplushchecklist.MainActivity;
import me.dyaika.fumoplushchecklist.R;
import me.dyaika.fumoplushchecklist.databinding.FragmentListBinding;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        Button b1 = root.findViewById(R.id.button1);
        b1.setOnClickListener(view -> {
            ((MainActivity)requireActivity()).setNavVisibility(true);
        });
        Button b2 = root.findViewById(R.id.button2);
        b2.setOnClickListener(view -> {
            ((MainActivity)requireActivity()).setNavVisibility(false);
        });
        textView.setText("List");
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity activity = (MainActivity) getActivity();
        //activity.setNavVisibility(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}