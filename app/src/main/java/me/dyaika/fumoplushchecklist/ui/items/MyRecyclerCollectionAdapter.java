package me.dyaika.fumoplushchecklist.ui.items;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.dyaika.fumoplushchecklist.R;
import me.dyaika.fumoplushchecklist.pojo.Item;

public class MyRecyclerCollectionAdapter extends RecyclerView.Adapter<MyRecyclerCollectionAdapter.ViewHolder> {
    private final static String TAG = "recycler collection adapter";
    private List<Item> items;
    private OnItemClickListener listener;
    private OnDeleteClickListener deleteClickListener;

    public MyRecyclerCollectionAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_deletable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.text.setText(item.getName());
        holder.rarity.setText(item.getRarity());
        holder.type.setText(item.getType());
        holder.image.setImageResource(R.drawable.ic_launcher_background);
        Picasso.get()
                .load(item.getImage_url())
                //.transform(new RoundedCornersTransformation(10, 0))
                .into(holder.image);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView text;
        public TextView rarity;
        public TextView type;
        public ImageButton delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.icon);
            text = itemView.findViewById(R.id.text);
            type = itemView.findViewById(R.id.type);
            rarity = itemView.findViewById(R.id.rarity);
            delete = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(items.get(position));
                }
            });
            delete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (deleteClickListener != null && position != RecyclerView.NO_POSITION) {
                    deleteClickListener.onItemClick(items.get(getAdapterPosition()));
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Item item);
    }
    public interface OnDeleteClickListener {
        void onItemClick(Item item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public void setOnDeleteClickListener(OnDeleteClickListener listener){
        this.deleteClickListener = listener;
    }
    public void update(List<Item> items1){
        this.items = items1;
        notifyDataSetChanged();
    }
}
