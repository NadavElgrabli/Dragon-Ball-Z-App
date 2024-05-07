package com.example.mydragonballzapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharactersActivity extends AppCompatActivity {

    private Button backButton;
    private Button searchCharacterByNameButton;
    private EditText searchCharacterByNameEditText;
    private JsonPlaceholderApi jsonPlaceholderApi;
    private List<Character> allCharacters;
    private CharacterAdapter characterAdapter;
    private RecyclerView recyclerViewCharacters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);
        findViewsById();
        setOnClickListeners();
        jsonPlaceholderApi = ApiClient.getClient().create(JsonPlaceholderApi.class);
        fetchAllCharacters();
    }

    public void findViewsById() {
        backButton = findViewById(R.id.button_back_to_main_menu);
        searchCharacterByNameButton = findViewById(R.id.button_search_character_by_name);
        searchCharacterByNameEditText = findViewById(R.id.edit_text_characters_name);
        recyclerViewCharacters = findViewById(R.id.recycler_view_characters);
    }

    // Method to set click listeners for buttons
    private void setOnClickListeners() {
        // Click listener for back button
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(CharactersActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Click listener for search button
        searchCharacterByNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCharacters();
            }
        });
    }


    public void fetchAllCharacters() {
        allCharacters = new ArrayList<>();
        fetchCharacters("https://dragonball-api.com/api/characters");

    }

    public void fetchCharacters(String url) {
        Call<CharacterResponse> call = jsonPlaceholderApi.getCharacterResponse(url);
        call.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                if (!response.isSuccessful()) {
                    // If response is not successful, log error and show toast message
                    Log.e("CharactersActivity", "Failed to fetch characters: " + response.code());
                    Toast.makeText(CharactersActivity.this, "Failed to fetch characters: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                CharacterResponse characterResponse = response.body();
                if (characterResponse != null) {
                    // If response is successful, extract characters data and check for pagination
                    List<Character> characterList = new ArrayList<>();
                    for (Character character : characterResponse.getItems()) {
                        characterList.add(character);
                    }

                    allCharacters.addAll(characterList);
                    String nextPageUrl = characterResponse.getLinks().getNext();

                    //if there is a next page, fetch it recursively
                    if (nextPageUrl != null && !nextPageUrl.isEmpty()) {
                        fetchCharacters(nextPageUrl);
                    } else {
                        // If there is no next page, set RecyclerView adapter with all characters
                        setRecyclerViewAdapter(allCharacters);
                    }
                }
            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                // If request fails, log error and show toast message
                Log.e("CharactersActivity", "Failed to fetch characters: " + t.getMessage());
                Toast.makeText(CharactersActivity.this, "Failed to fetch characters: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Method to search characters by name
    private void searchCharacters() {
        String query = searchCharacterByNameEditText.getText().toString().trim().toLowerCase();
        if (query.isEmpty()) {
            // If search query is empty, set RecyclerView adapter with all people
            setRecyclerViewAdapter(allCharacters);
        } else {
            // If search query is not empty, filter people list by name
            List<Character> searchResult = new ArrayList<>();
            for (Character character : allCharacters) {
                if (character.getName().toLowerCase().equals(query)) {
                    searchResult.add(character);
                }
            }
            if (!searchResult.isEmpty()) { // Check if searchResult is not empty
                // If search result is not empty, set RecyclerView adapter with search results
                setRecyclerViewAdapter(searchResult);
            } else {
                // If search result is empty, show toast message
                Toast.makeText(this, "Character not found", Toast.LENGTH_LONG).show();
            }
        }
    }


    // Method to set RecyclerView adapter
    private void setRecyclerViewAdapter(List<Character> characterList) {
        characterAdapter = new CharacterAdapter(characterList, CharactersActivity.this);
        recyclerViewCharacters.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCharacters.setAdapter(characterAdapter);
    }
}