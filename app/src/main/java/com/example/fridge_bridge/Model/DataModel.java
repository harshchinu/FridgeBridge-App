package com.example.fridge_bridge.Model;

public class DataModel {
    public String getItemname() {
        return itemname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String url;

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public DataModel(String itemname) {
        this.itemname = itemname;
    }


    String itemname;
    int value;
}
