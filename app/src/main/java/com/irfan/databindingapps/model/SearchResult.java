package com.irfan.databindingapps.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Irfan Irawan S on 05-Jan-16.
 */
public class SearchResult {
    public int mTotalItems;

    @SerializedName("items")
    public List<Book> books;
}
