package com.irfan.databindingapps.apiservice;

import com.irfan.databindingapps.model.SearchResult;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Irfan Irawan S on 05-Jan-16.
 */
public interface GoogleBooksService {
    @GET("/books/v1/volumes")
    void search(@Query("q") String query, Callback<SearchResult> callback);
}
