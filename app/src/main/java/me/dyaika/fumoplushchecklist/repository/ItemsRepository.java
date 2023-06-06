package me.dyaika.fumoplushchecklist.repository;

import java.util.ArrayList;
import java.util.List;

import me.dyaika.fumoplushchecklist.pojo.Item;

public class ItemsRepository {
    public static List<Item> getItems(){
        List<Item> items = new ArrayList<>();
        items.add(new Item(
                1,
                "Hakurei Reimu",
                "V1",
                2008,
                "Regular Fumo",
                2700,
                "Uncommon",
                "Мягкие Игрушки Фумо Fumo - это мини версии знаменитых героев и персонажей из серии компьютерных игр Touhou Project (Проект \"Восток\"). Сделаны из качественного хлопка и плюша, приятны на ощупь, безопасные элементы.",
                "https://fumo.website/img/001.jpg",
                "https://www.gift-gift.jp/nui/nui001.html"
        ));
        items.add(new Item(
                2,
                "Kirisame Marisa",
                "V1",
                2008,
                "Regular Fumo",
                2700,
                "Uncommon",
                "Мягкие Игрушки Фумо Fumo - это мини версии знаменитых героев и персонажей из серии компьютерных игр Touhou Project (Проект \"Восток\"). Сделаны из качественного хлопка и плюша, приятны на ощупь, безопасные элементы.",
                "https://fumo.website/img/002.jpg",
                "https://www.gift-gift.jp/nui/nui002.html"
        ));
        items.add(new Item(
                3,
                "Izayoi Sakuya",
                "V1",
                2009,
                "Regular Fumo",
                3024,
                "Rare",
                "Мягкие Игрушки Фумо Fumo - это мини версии знаменитых героев и персонажей из серии компьютерных игр Touhou Project (Проект \"Восток\"). Сделаны из качественного хлопка и плюша, приятны на ощупь, безопасные элементы.",
                "https://fumo.website/img/004.jpg",
                "https://www.gift-gift.jp/nui/nui004.html"
        ));
        items.add(new Item(
                4,
                "Remilia Scarlet",
                "V1",
                2009,
                "Regular Fumo",
                3024,
                "Common",
                "Мягкие Игрушки Фумо Fumo - это мини версии знаменитых героев и персонажей из серии компьютерных игр Touhou Project (Проект \"Восток\"). Сделаны из качественного хлопка и плюша, приятны на ощупь, безопасные элементы.",
                "https://fumo.website/img/005.jpg",
                "https://www.gift-gift.jp/nui/nui005.html"
        ));
        return items;
    }

    public static Item getItemByID(int id) {
        if (id > 0){
            return new Item(
                    4,
                    "Remilia Scarlet",
                    "V1",
                    2009,
                    "Regular Fumo",
                    3024,
                    "Common",
                    "Мягкие Игрушки Фумо Fumo - это мини версии знаменитых героев и персонажей из серии компьютерных игр Touhou Project (Проект \"Восток\"). Сделаны из качественного хлопка и плюша, приятны на ощупь, безопасные элементы.",
                    "https://fumo.website/img/005.jpg",
                    "https://www.gift-gift.jp/nui/nui005.html"
            );
        }
        return new Item(
                0,
                "Такого товара нет",
                "-",
                0,
                "-",
                0,
                "-",
                "Мягкие Игрушки Фумо Fumo - это мини версии знаменитых героев и персонажей из серии компьютерных игр Touhou Project (Проект \"Восток\"). Сделаны из качественного хлопка и плюша, приятны на ощупь, безопасные элементы.",
                "https://i.imgur.com/S5Watmq.jpg",
                "https://www.gift-gift.jp/nui/nui00001.html"
        );
    }
}
