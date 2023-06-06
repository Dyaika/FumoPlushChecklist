package me.dyaika.fumoplushchecklist.ui.items;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.dyaika.fumoplushchecklist.MainActivity;
import me.dyaika.fumoplushchecklist.R;
import me.dyaika.fumoplushchecklist.model.AccountViewModel;
import me.dyaika.fumoplushchecklist.pojo.Item;
import me.dyaika.fumoplushchecklist.repository.ItemsRepository;

public class ItemFragment extends Fragment {

    public static final String ARG_PARAM = "fumo_id";
    private Item item;

    private TextView descriptionView;
    private TextView nameView;
    private TextView costView;
    private ImageView imageView;
    private Button storeButton;
    private TextView yearView;
    private TextView versionView;
    private CheckBox favorite;
    AccountViewModel accountViewModel;

    private int fumo_id;

    public ItemFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountViewModel = ((MainActivity)requireActivity())
                .getViewModelProvider()
                .get(AccountViewModel.class);
        if (getArguments() != null) {
            fumo_id = getArguments().getInt(ARG_PARAM);
        } else {
            fumo_id = -1;
        }
        item = ItemsRepository.getItemByID(fumo_id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.imageView);
        descriptionView = view.findViewById(R.id.description);
        nameView = view.findViewById(R.id.name);
        costView = view.findViewById(R.id.cost);
        storeButton = view.findViewById(R.id.store_button);
        favorite = view.findViewById(R.id.favorite);
        yearView = view.findViewById(R.id.year);
        versionView = view.findViewById(R.id.version);
        contentInflation();

    }
    private void contentInflation(){
        Picasso.get()
                .load(item.getImage_url())
                .into(imageView);
        descriptionView.setText(item.getDescryption());
        nameView.setText(item.getName());
        costView.setText(item.getCost() + "¥");
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = item.getStore_url();

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        if (Boolean.FALSE.equals(accountViewModel.isLoggedIn().getValue())){
            favorite.setVisibility(View.GONE);
        } else {
            favorite.setChecked(accountViewModel.isFavorite(fumo_id));
        }
        yearView.setText(item.getRelease_year() + "");
        versionView.setText(item.getVersion());
    }
}