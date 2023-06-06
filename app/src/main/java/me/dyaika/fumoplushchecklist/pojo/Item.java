package me.dyaika.fumoplushchecklist.pojo;


public class Item {
    private String name;
    private String version;
    private int release_year;
    private String type;
    private int id;
    private int cost;
    private String rarity;

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public int getRelease_year() {
        return release_year;
    }

    public String getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    public String getRarity() {
        return rarity;
    }

    public String getDescryption() {
        return descryption;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getStore_url() {
        return store_url;
    }

    public int getId() {
        return id;
    }

    public Item(int id, String name, String version, int release_year, String type, int cost, String rarity, String descryption, String image_url, String store_url) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.release_year = release_year;
        this.type = type;
        this.cost = cost;
        this.rarity = rarity;
        this.descryption = descryption;
        this.image_url = image_url;
        this.store_url = store_url;
    }

    private String descryption;
    private String image_url;
    private String store_url;

}