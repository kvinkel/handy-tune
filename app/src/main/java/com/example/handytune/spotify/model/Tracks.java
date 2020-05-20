package com.example.handytune.spotify.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tracks {

    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("total")
    @Expose
    private Integer total;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
