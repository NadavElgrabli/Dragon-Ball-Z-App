package com.example.mydragonballzapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface JsonPlaceholderApi {

    @GET
    Call<CharacterResponse> getCharacterResponse(@Url String url);

}