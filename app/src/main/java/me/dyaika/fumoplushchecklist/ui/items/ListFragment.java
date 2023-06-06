package me.dyaika.fumoplushchecklist.ui.items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.dyaika.fumoplushchecklist.MainActivity;
import me.dyaika.fumoplushchecklist.R;
import me.dyaika.fumoplushchecklist.databinding.FragmentListBinding;
import me.dyaika.fumoplushchecklist.pojo.Item;
import me.dyaika.fumoplushchecklist.repository.ItemsRepository;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;
    private NavController navController;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        MainActivity activity = (MainActivity) getActivity();
        //activity.setNavVisibility(true);
        List<Item> items = ItemsRepository.getItems();
        // Inflate the layout for this fragment
        RecyclerView recyclerView = view.findViewById(R.id.itemsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        int space = 16;
        recyclerView.addItemDecoration(new SpaceItemDecoration(space));
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(items);
        adapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                Bundle args = new Bundle();
                args.putInt(ItemFragment.ARG_PARAM, item.getId());
                navController.navigate(R.id.action_navigation_list_to_itemFragment, args);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}