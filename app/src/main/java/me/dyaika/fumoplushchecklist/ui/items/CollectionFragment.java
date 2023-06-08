package me.dyaika.fumoplushchecklist.ui.items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import me.dyaika.fumoplushchecklist.MainActivity;
import me.dyaika.fumoplushchecklist.R;
import me.dyaika.fumoplushchecklist.databinding.FragmentCollectionBinding;
import me.dyaika.fumoplushchecklist.logic.AccountViewModel;
import me.dyaika.fumoplushchecklist.logic.ItemsViewModel;
import me.dyaika.fumoplushchecklist.pojo.Item;
import me.dyaika.fumoplushchecklist.pojo.User;

public class CollectionFragment extends Fragment {

    private FragmentCollectionBinding binding;
    private NavController navController;
    private ItemsViewModel itemsViewModel;
    private AccountViewModel accountViewModel;
    TextView placeholder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentCollectionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        placeholder = binding.placeholder;
        itemsViewModel = ((MainActivity)requireActivity())
                .getViewModelProvider()
                .get(ItemsViewModel.class);

        accountViewModel = ((MainActivity)requireActivity())
                .getViewModelProvider()
                .get(AccountViewModel.class);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        ((MainActivity)activity).setNavVisibility(true);

        navController = Navigation.findNavController(view);
        RecyclerView recyclerView = view.findViewById(R.id.itemsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        int space = 16;
        recyclerView.addItemDecoration(new SpaceItemDecoration(space));
        MyRecyclerCollectionAdapter adapter = new MyRecyclerCollectionAdapter(new ArrayList<Item>());
        accountViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            Map<Integer, Item> items = itemsViewModel.getItemsLiveData().getValue();
            List<Item> itemList = new ArrayList<>();
            if (user != null && items != null){
                for (int id:
                     user.getFavorite()) {
                    Item item = items.get(id);
                    if (item != null){
                        itemList.add(item);
                    }
                }
                checkEmpty(itemList.isEmpty());
                adapter.update(itemList);
            }
        });
        adapter.setOnItemClickListener(item -> {
            Bundle args = new Bundle();
            args.putInt(ItemFragment.ARG_PARAM, item.getId());
            navController.navigate(R.id.action_navigation_collection_to_itemFragment, args);
        });
        adapter.setOnDeleteClickListener(item -> {
            accountViewModel.setFavorite(item.getId(), false);
            //Toast.makeText(getContext(), item.getName(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void checkEmpty(boolean isempty){
        if (isempty){
            placeholder.setVisibility(View.VISIBLE);
        } else {
            placeholder.setVisibility(View.GONE);
        }
    }
}