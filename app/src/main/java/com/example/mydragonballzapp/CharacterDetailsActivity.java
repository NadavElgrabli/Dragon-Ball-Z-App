package com.example.mydragonballzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class CharacterDetailsActivity extends AppCompatActivity {
    private TextView characterName;
    private TextView characterRace;
    private TextView characterGender;
    private ImageView characterImage;
    private Button backToMainMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);
        findViewsById();
        setOnClickListeners();
        displayCharacterData();
    }

    public void findViewsById() {
        characterName = findViewById(R.id.text_view_character_name);
        characterRace = findViewById(R.id.text_view_character_race);
        characterGender = findViewById(R.id.text_view_character_gender);
        characterImage = findViewById(R.id.image_view_character_image);
        backToMainMenuButton = findViewById(R.id.button_back_to_main_menu);
    }

    public void setOnClickListeners() {
        backToMainMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(CharacterDetailsActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }


    private void displayCharacterData() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("characterJson")) {
            String characterJson = intent.getStringExtra("characterJson");
            Gson gson = new Gson();
            Character character = gson.fromJson(characterJson, Character.class);
            if (character != null) {
                characterName.setText(character.getName());
                characterRace.setText("Race: " + character.getRace());
                characterGender.setText("Gender: "+ character.getGender());

                // show image of character in ImageView, the image is in character. getImage() and its in url format
                // use picasso or glide to load image from url
                 Picasso.get().load(character.getImage()).into(characterImage);

            }
        }
    }
}