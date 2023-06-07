package me.dyaika.fumoplushchecklist.logic;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.dyaika.fumoplushchecklist.pojo.Item;

public class ItemsViewModel extends ViewModel {
    private MutableLiveData<Map<Integer, Item>> itemsLiveData = new MutableLiveData<>();
    public LiveData<Map<Integer, Item>> getItemsLiveData() {
        return itemsLiveData;
    }
    public void loadItems(Context context) {
        // Создайте новый поток для чтения данных из файла
        new Thread(() -> {
            List<Item> itemList = new ArrayList<>();
            String json = "[]";
            AssetManager assetManager = context.getAssets();
            try {
                InputStream inputStream = assetManager.open("items.json");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                json = stringBuilder.toString();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Gson gson = new Gson();
            Item[] items = gson.fromJson(json, Item[].class);


            Map<Integer, Item> itemMap = new HashMap<>();
            for (Item item:
                 items) {
                itemMap.put(item.getId(), item);
            }


            // Live Data update
            itemsLiveData.postValue(itemMap);
            Log.d("items", "loading finished");
        }).start();
    }
}
