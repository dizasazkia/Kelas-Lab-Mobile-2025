package com.example.praktikum_6;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/character")
    Call<CharacterResponse> getCharacters(@Query("page") int page);

    @GET("api/character/{id}")
    Call<Character> getCharacterDetail(@Path("id") int id);
}
